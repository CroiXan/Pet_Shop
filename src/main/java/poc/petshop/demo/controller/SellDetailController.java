package poc.petshop.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import poc.petshop.demo.model.SellDetail;
import poc.petshop.demo.service.ProductService;
import poc.petshop.demo.service.SellDetailService;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/sellDetail")
public class SellDetailController {

    @Autowired
    private ProductService productService;

    @Autowired
    private SellDetailService sellDetailService;

    @GetMapping
    public CollectionModel<EntityModel<SellDetail>> getAllSellDetail() {
        List<SellDetail> sellDetails = sellDetailService.getSellDetails();

        List<EntityModel<SellDetail>> sellDetailResource = sellDetails.stream()
            .map(sellDetail -> EntityModel.of(sellDetail,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getSellDetailById(sellDetail.getId())).withSelfRel()
            ))
            .collect(Collectors.toList());
        
        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllSellDetail());

        CollectionModel<EntityModel<SellDetail>> resources = CollectionModel.of(sellDetailResource, linkTo.withRel("sellDetail"));

        return resources;
    }
    
    @GetMapping("/{id}")
    public EntityModel<SellDetail> getSellDetailById(@PathVariable Long id) {

        Optional<SellDetail> pelicula = sellDetailService.getSellDetailById(id);

        if (pelicula.isPresent()) {
            return EntityModel.of(pelicula.get(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getSellDetailById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllSellDetail()).withRel("all-sellDetails")
            );
        }else{
            throw new SellDetailNotFoundException("Detalle de venta no encontrado con id: " + id);
        }

    }

    @PostMapping
    public EntityModel<SellDetail> addSellDetail(@RequestBody SellDetail sellDetail) {
        
        if(sellDetail.getId() != null && sellDetail.getId() < 0L){
            throw new SellDetailBadRequestException("id de venta no puede ser un valor negativo");
        }

        sellDetail.setId(null);

        if(sellDetail.getIdProduct() == null){
            throw new SellDetailBadRequestException("id de producto no puede estar vacio");
        }

        if(sellDetail.getIdProduct() < 0L){
            throw new SellDetailBadRequestException("id de producto no puede ser un valor negativo");
        }

        if (sellDetail.getSellPrice() < 0) {
            throw new SellDetailBadRequestException("SellPrice no puede ser un valor negativo.");
        }

        if (!productService.existsProductById(sellDetail.getIdProduct())) {
            throw new ProductNotFoundException("producto no encontrado");
        }

        SellDetail createdSellDetail = sellDetailService.createSellDetail(sellDetail);
        return EntityModel.of(createdSellDetail,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getSellDetailById(createdSellDetail.getId())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllSellDetail()).withRel("all-sellDetails")
        );

    }
    
    @PutMapping("/{id}")
    public EntityModel<SellDetail> updateSellDetail(@PathVariable Long id, @RequestBody SellDetail sellDetail) {
        
        if (sellDetail.getId() == null) {
            throw new SellDetailBadRequestException("id de venta no puede estar vacio");
        }

        if(sellDetail.getId() < 0L){
            throw new SellDetailBadRequestException("id no puede ser un valor negativo");
        }

        if(sellDetail.getIdProduct() == null){
            throw new SellDetailBadRequestException("id de producto no puede estar vacio");
        }

        if(sellDetail.getIdProduct() < 0L){
            throw new SellDetailBadRequestException("id de producto no puede ser un valor negativo");
        }

        if (sellDetail.getSellPrice() < 0) {
            throw new SellDetailBadRequestException("SellPrice no puede ser un valor negativo.");
        }

        if (!productService.existsProductById(sellDetail.getIdProduct())) {
            throw new ProductNotFoundException("producto no encontrado");
        }

        if (!sellDetailService.existsSellDetailById(sellDetail.getId())) {
            throw new SellDetailNotFoundException("venta no encontrada");
        }

        SellDetail updatedSellDetail = sellDetailService.updateSellDetail(id, sellDetail);
        return EntityModel.of(updatedSellDetail,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getSellDetailById(id)).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllSellDetail()).withRel("all-sellDetails")
        );

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSellDetail(@PathVariable String id){
        
        Long parsedId = validateInteger(id, "id");

        if (!sellDetailService.existsSellDetailById(parsedId)) {
            throw new SellDetailNotFoundException("venta no encontrada");
        }

        sellDetailService.deleteSellDetail(parsedId);

        return ResponseEntity.ok().body("Venta " + parsedId + " borrada.");
    }

    private Long validateInteger(String intAsStr,String paramName){
        try {
            Long parsedInt = Long.parseLong(intAsStr);

            if(parsedInt < 0){
                parsedInt = -1L;
            }

            return parsedInt;
        } catch (Exception e) {
            return -1L;
        }
    }
    
}
