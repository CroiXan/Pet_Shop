package poc.petshop.demo.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RestController;

import poc.petshop.demo.model.IncomeDetail;
import poc.petshop.demo.model.ParsedInt;
import poc.petshop.demo.model.ParsedLong;
import poc.petshop.demo.model.Product;
import poc.petshop.demo.model.SellDetail;
import poc.petshop.demo.service.ProductService;
import poc.petshop.demo.service.SellDetailService;
import poc.petshop.demo.service.ServiceUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private SellDetailService sellDetailService;

    private ServiceUtils serviceUtils;

    @GetMapping
    public CollectionModel<EntityModel<Product>> getProducts(){

        List<Product> products = productService.getAllProducts();

        List<EntityModel<Product>> productResource = products.stream()
            .map(product -> EntityModel.of(product,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getProductById(product.getId())).withSelfRel()
            ))
            .collect(Collectors.toList());
        
        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getProducts());

        CollectionModel<EntityModel<Product>> resources = CollectionModel.of(productResource, linkTo.withRel("product"));

        return resources;

    }

    @GetMapping("/{id}")
    public EntityModel<Product> getProductById(@PathVariable Long id) {

        Optional<Product> product = productService.getProductById(id);

        if (product.isPresent()) {
            return EntityModel.of(product.get(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getProductById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getProducts()).withRel("all-products")
            );
        }else{
            throw new ProductNotFoundException("Producto no encontrada con Id: " + id);
        }
    
    }

    @PostMapping
    public EntityModel<Product> postAddProduct(@RequestBody Product product) {

        if(product.getId() != null && product.getId() < 0L){
            throw new ProductBadRequestException("id no puede ser un valor negativo");
        }

        product.setId(null);

        if (product.getName().length() == 0) {
            throw new ProductBadRequestException("name no puede estar vacio");
        }

        if (product.getDescription().length() == 0) {
            throw new ProductBadRequestException("description no puede estar vacio");
        }

        if (product.getPurchasePrice() <= 0) {
            throw new ProductBadRequestException("PurchasePrice no puede ser un valor negativo ni cero");
        }
        
        if (product.getQuantity() <= 0) {
            throw new ProductBadRequestException("Quantity no puede ser un valor negativo ni cero");
        }
        
        Product createdProduct = productService.createProduct(product);
        return EntityModel.of(createdProduct,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getProductById(createdProduct.getId())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getProducts()).withRel("all-products")
        );

    }

    @PutMapping("/{id}")
    public EntityModel<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        
        if (product.getId() == null) {
            throw new ProductBadRequestException("id no puede estar vacio");
        }

        if(product.getId() < 0L){
            throw new ProductBadRequestException("id no puede ser un valor negativo");
        }

        if (product.getName().length() == 0) {
            throw new ProductBadRequestException("name no puede estar vacio");
        }

        if (product.getDescription().length() == 0) {
            throw new ProductBadRequestException("description no puede estar vacio");
        }

        if (product.getPurchasePrice() <= 0) {
            throw new ProductBadRequestException("PurchasePrice no puede ser un valor negativo ni cero");
        }
        
        if (product.getQuantity() <= 0) {
            throw new ProductBadRequestException("Quantity no puede ser un valor negativo ni cero");
        }

        if (!productService.existsProductById(product.getId())) {
            throw new ProductBadRequestException("producto no encontrado");
        }
        
        Product updatedProduct = productService.updateProduct(id, product);
        return EntityModel.of(updatedProduct,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getProductById(id)).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getProducts()).withRel("all-products")
        );

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id){
        
        ParsedLong parsedId = serviceUtils.validateLong(id, "id");
        
        if(!parsedId.isSuccess()){
            throw new ProductBadRequestException(parsedId.getErrorMessage());
        }

        if (!productService.existsProductById(parsedId.getResultLong())) {
            throw new ProductBadRequestException("producto no encontrado");
        }

        List<SellDetail> sellDetails = sellDetailService.getSellDetails();

        for (SellDetail sellDetail : sellDetails) {
            if (sellDetail.getIdProduct() == parsedId.getResultLong()) {
                sellDetailService.deleteSellDetail(sellDetail.getId());
            }
        }

        productService.deleteProduct(parsedId.getResultLong());

        return ResponseEntity.ok().body("Product " + parsedId.getResultLong() + " borrado.");
    }

    @GetMapping("/{id}/profit/{year}-{month}-{day}")
    public ResponseEntity<?> calcProfitByDate(@PathVariable String id,@PathVariable String year,@PathVariable String month,@PathVariable String day) {

        ParsedLong parsedId = serviceUtils.validateLong(id,"id");
        ParsedInt parsedYear = serviceUtils.validateYear(year);
        ParsedInt parsedMonth = serviceUtils.validateMonth(month);
        ParsedInt parsedDay = serviceUtils.validateDay(day);

        if (!parsedId.isSuccess()) {
            throw new ProductBadRequestException(parsedId.getErrorMessage());
        }

        if (!parsedYear.isSuccess()) {
            throw new ProductBadRequestException(parsedYear.getErrorMessage());
        }

        if (!parsedMonth.isSuccess()) {
            throw new ProductBadRequestException(parsedMonth.getErrorMessage());
        }

        if (!parsedDay.isSuccess()) {
            throw new ProductBadRequestException(parsedDay.getErrorMessage());
        }

        Optional<Product> product = productService.getProductById(parsedId.getResultLong());

        if (product.isEmpty()) {
            throw new ProductNotFoundException("producto no encontrado");
        }

        IncomeDetail incomeDetail = serviceUtils.calcEarning(product.get(), parsedYear.getResultInt(), parsedMonth.getResultInt(), parsedDay.getResultInt(),sellDetailService.getSellDetails());
        return  ResponseEntity.ok(incomeDetail);
        
    }

    @GetMapping("/profit/{year}-{month}-{day}")
    public ResponseEntity<?> calcAllProfitByDate(@PathVariable String year,@PathVariable String month,@PathVariable String day) {

        ParsedInt parsedYear = serviceUtils.validateYear(year);
        ParsedInt parsedMonth = serviceUtils.validateMonth(month);
        ParsedInt parsedDay = serviceUtils.validateDay(day);

        if (!parsedYear.isSuccess()) {
            throw new ProductBadRequestException(parsedYear.getErrorMessage());
        }

        if (!parsedMonth.isSuccess()) {
            throw new ProductBadRequestException(parsedMonth.getErrorMessage());
        }

        if (!parsedDay.isSuccess()) {
            throw new ProductBadRequestException(parsedDay.getErrorMessage());
        }

        String period = year + "";
        IncomeDetail localIncomeCalc = new IncomeDetail(LocalDateTime.now(), "", 0, 0);

        if(parsedMonth.getResultInt() > 0 ){
            period += "-"+month;
            if (parsedDay.getResultInt() > 0) {
                period += "-"+day;
            }
        }

        localIncomeCalc = serviceUtils.calcEarning(null, parsedYear.getResultInt(), parsedMonth.getResultInt(), parsedDay.getResultInt(),sellDetailService.getSellDetails());

        if (localIncomeCalc == null) {
            throw new ProductBadRequestException("fecha no valida");
        }

        localIncomeCalc.setPeriod(period);

        return  ResponseEntity.ok(localIncomeCalc);
    }

}

