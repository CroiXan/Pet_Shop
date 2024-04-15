package poc.petshop.demo.controller;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;

import poc.petshop.demo.model.ErrorMessage;
import poc.petshop.demo.model.IncomeDetail;
import poc.petshop.demo.model.Product;
import poc.petshop.demo.model.SellDetail;
import poc.petshop.demo.service.IncomeDetailService;
import poc.petshop.demo.service.ProductService;
import poc.petshop.demo.service.SellDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private IncomeDetailService incomeDetailService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SellDetailService sellDetailService;

    private ResponseEntity<ErrorMessage> error = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(HttpStatus.NOT_FOUND.value(),"producto no encontrado"));
    
    @GetMapping
    public ResponseEntity<List<Product>> getProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable String id) {

        Long parsedId = validateInteger(id,"id");
        Optional<Product> product = productService.getProductById(parsedId);

        if (product.isEmpty()) {
            return buildResponseError(HttpStatus.NOT_FOUND,"producto no encontrado");
        }

        return ResponseEntity.ok(product);
    
    }

    @PostMapping
    public ResponseEntity<?> postAddProduct(@RequestBody Product product) {

        if(product.getId() != null && product.getId() < 0L){
            return buildResponseError(HttpStatus.BAD_REQUEST,"id no puede ser un valor negativo");
        }

        product.setId(null);

        if (product.getName().length() == 0) {
            return buildResponseError(HttpStatus.BAD_REQUEST,"name no puede estar vacio");
        }

        if (product.getDescription().length() == 0) {
            return buildResponseError(HttpStatus.BAD_REQUEST,"description no puede estar vacio");
        }

        if (product.getPurchasePrice() <= 0) {
            return buildResponseError(HttpStatus.BAD_REQUEST,"PurchasePrice no puede ser un valor negativo ni cero");
        }
        
        if (product.getQuantity() <= 0) {
            return buildResponseError(HttpStatus.BAD_REQUEST,"Quantity no puede ser un valor negativo ni cero");
        }
        
        return ResponseEntity.ok(productService.createProduct(product));
    }

    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestBody Product product) {
        
        if (product.getId() == null) {
            return buildResponseError(HttpStatus.BAD_REQUEST,"id no puede estar vacio");
        }

        if(product.getId() < 0L){
            return buildResponseError(HttpStatus.BAD_REQUEST,"id no puede ser un valor negativo");
        }

        if (product.getName().length() == 0) {
            return buildResponseError(HttpStatus.BAD_REQUEST,"name no puede estar vacio");
        }

        if (product.getDescription().length() == 0) {
            return buildResponseError(HttpStatus.BAD_REQUEST,"description no puede estar vacio");
        }

        if (product.getPurchasePrice() <= 0) {
            return buildResponseError(HttpStatus.BAD_REQUEST,"PurchasePrice no puede ser un valor negativo ni cero");
        }
        
        if (product.getQuantity() <= 0) {
            return buildResponseError(HttpStatus.BAD_REQUEST,"Quantity no puede ser un valor negativo ni cero");
        }

        if (!productService.existsProductById(product.getId())) {
            return buildResponseError(HttpStatus.NOT_FOUND,"producto no encontrado");
        }
        
        return ResponseEntity.ok(productService.updateProduct(product.getId(),product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id){
        
        Long parsedId = validateInteger(id, "id");

        if(parsedId == -1){
            return error;
        }

        if (!productService.existsProductById(parsedId)) {
            return buildResponseError(HttpStatus.NOT_FOUND,"producto no encontrado");
        }

        List<IncomeDetail> incomeDetails = incomeDetailService.getIncomeDetails();
        List<SellDetail> sellDetails = sellDetailService.getSellDetails();

        for (SellDetail sellDetail : sellDetails) {
            if (sellDetail.getIdProduct() == parsedId) {
                sellDetailService.deleteSellDetail(sellDetail.getId());
            }
        }
        for (IncomeDetail incomeDetail : incomeDetails) {
            if (incomeDetail.getIdProduct() == parsedId) {
                incomeDetailService.deleteIncomeDetail(incomeDetail.getId());
            }
        }

        productService.deleteProduct(parsedId);

        return ResponseEntity.ok().body("Product " + parsedId + " borrado.");
    }

    @GetMapping("/{id}/profit/{year}-{month}-{day}")
    public ResponseEntity<?> calcProfitByDate(@PathVariable String id,@PathVariable String year,@PathVariable String month,@PathVariable String day) {

        Long parsedId = validateInteger(id,"id");
        int parsedYear = validateYear(year);
        int parsedMonth = validateMonth(month);
        int parsedDay = validateDay(day);

        if (parsedId == -1 ||parsedYear == -1 || parsedMonth == -1 || parsedDay == -1) {
            return error;
        }

        Optional<Product> product = productService.getProductById(parsedId);

        if (product.isEmpty()) {
            return buildResponseError(HttpStatus.NOT_FOUND,"producto no encontrado");
        }

        IncomeDetail incomeDetail = calcEarning(product.get(), parsedYear, parsedMonth, parsedDay);
        return  ResponseEntity.ok(incomeDetail);
        
    }

    @GetMapping("/profit/{year}-{month}-{day}")
    public ResponseEntity<?> calcAllProfitByDate(@PathVariable String year,@PathVariable String month,@PathVariable String day) {

        int parsedYear = validateYear(year);
        int parsedMonth = validateMonth(month);
        int parsedDay = validateDay(day);
        String period = year + "";
        IncomeDetail localIncomeCalc = new IncomeDetail(LocalDateTime.now(), "", 0, 0);

        if (parsedYear == -1 || parsedMonth == -1 || parsedDay == -1) {
            return error;
        }

        if(parsedMonth > 0 ){
            period += "-"+month;
            if (parsedDay > 0) {
                period += "-"+day;
            }
        }

        localIncomeCalc = calcEarning(null, parsedYear, parsedMonth, parsedDay);

        if (localIncomeCalc == null) {
            return error;
        }

        localIncomeCalc.setPeriod(period);

        return  ResponseEntity.ok(localIncomeCalc);
    }

    private Long validateInteger(String intAsStr,String paramName){
        try {
            Long parsedInt = Long.parseLong(intAsStr);

            if(parsedInt < 0){
                parsedInt = -1L;
                error = buildResponseError(HttpStatus.BAD_REQUEST,paramName+" no puede ser negativo");
            }

            return parsedInt;
        } catch (Exception e) {
            error = buildResponseError(HttpStatus.BAD_REQUEST,paramName+" no valido");
            return -1L;
        }
    }

    private int validateYear(String yearStr){
        if(yearStr.length() == 4){
            return Long.valueOf(validateInteger(yearStr,"año")).intValue();
        }else{
            error = buildResponseError(HttpStatus.BAD_REQUEST,"valor de año no valido");
        }
        return -1;
    }

    private int validateMonth(String monthStr){
        int monthLength = monthStr.length();

        if(monthLength == 0){
            return 0;
        }

        if( monthLength == 1 || monthLength == 2){
            int monthInt = Long.valueOf(validateInteger(monthStr,"mes")).intValue();
            if(monthInt < 1 || monthInt > 12){
                monthInt = -1;
                error = buildResponseError(HttpStatus.BAD_REQUEST,"numero de mes no valido");
            }
            return monthInt;
        }else{
            error = buildResponseError(HttpStatus.BAD_REQUEST,"valor de mes no valido");
        }

        return -1;
    }

    private int validateDay(String dayStr){
        int dayLength = dayStr.length();

        if(dayLength == 0){
            return 0;
        }

        if(dayLength == 1 || dayLength == 2){
            int dayInt = Long.valueOf(validateInteger(dayStr,"dia")).intValue();
            if(dayInt < 1 || dayInt > 31){
                dayInt = -1;
                error = buildResponseError(HttpStatus.BAD_REQUEST,"numero de dia no valido");
            }
            return dayInt;
        }else{
            error = buildResponseError(HttpStatus.BAD_REQUEST,"valor de dia no valido");
        }

        return -1;
    }

    private IncomeDetail calcEarning(Product product,int year, int month, int day){
        double income = 0.0;
        double earning = 0.0;
        String period = year+"";

        if (month > 0) {
            period += "-" + month;
        }

        if(year > 0 && month > 0 && day > 0){
            try {

                LocalDate searchDate = LocalDate.of(year, month, day);
                period += "-" + day;

                List<SellDetail> sellDetails = sellDetailService.getSellDetails();

                for (SellDetail sellDetail : sellDetails) {
                    if (sellDetail.getIdProduct() == product.getId() || product == null) {
                        if (sellDetail.getSellDateTime().toLocalDate().equals(searchDate)) {
                            income += sellDetail.getSellPrice();
                            earning += sellDetail.getSellPrice() * 0.81 - product.getPurchasePrice();
                        }
                    }
                }

            } catch (Exception e) {
                error = buildResponseError(HttpStatus.BAD_REQUEST,"fecha no valida");
                return null;
            }
        }else{

            List<SellDetail> sellDetails = sellDetailService.getSellDetails();

            for (SellDetail sellDetail : sellDetails) {
                if (sellDetail.getIdProduct() == product.getId() || product == null) {
                    if ( sellDetail.getSellDateTime().getYear() == year && ( month == 0 || sellDetail.getSellDateTime().getMonthValue() == month ) ) {
                        income += sellDetail.getSellPrice();
                        earning += sellDetail.getSellPrice() * 0.81 - product.getPurchasePrice();
                    }
                }
            }

        }

        DecimalFormat format = new DecimalFormat("#.##");
        
        return (new IncomeDetail(1L,product.getId(),LocalDateTime.now(), period, income,Double.parseDouble(format.format(earning))));
        
    }
    
    private ResponseEntity<ErrorMessage> buildResponseError(HttpStatus status,String message){
        return ResponseEntity.status(status).body(new ErrorMessage(status.value(),message));
    }
}

