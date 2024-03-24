package poc.petshop.demo;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
public class ProductController {

    private List<Product> productList = new ArrayList<>();
    private ResponseEntity<ErrorMessage> error = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(HttpStatus.NOT_FOUND.value(),"producto no encontrado"));

    public ProductController() {
        productList.add(new Product(1, "plato verde", "plato plastico color verde", 2000, 100));
        productList.get(0).addSellDetail(new SellDetail(3000, LocalDateTime.of(2023, Month.DECEMBER, 15, 15, 35, 0)));
        productList.get(0).addSellDetail(new SellDetail(3000, LocalDateTime.of(2023, Month.DECEMBER, 15, 16, 20, 0)));
        productList.get(0).addSellDetail(new SellDetail(2500, LocalDateTime.of(2024, Month.MARCH, 19, 10, 00, 0)));
        productList.get(0).addSellDetail(new SellDetail(2500, LocalDateTime.of(2024, Month.MARCH, 19, 15, 51, 0)));
        productList.get(0).addSellDetail(new SellDetail(3200, LocalDateTime.of(2024, Month.MARCH, 20, 14, 13, 0)));
        productList.get(0).addSellDetail(new SellDetail(3200, LocalDateTime.of(2024, Month.MARCH, 20, 16,48, 0)));
    }
    
    @GetMapping("/product")
    public List<Product> getProducts(){
        return productList;
    }

    
    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProductById(@PathVariable String id) {

        int parsedId = validateInteger(id,"id");

        if(parsedId==-1){
            return error;
        }

        for (Product product : productList) {
            if (parsedId == product.getId()) {
                return ResponseEntity.ok(product);
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(HttpStatus.NOT_FOUND.value(),"producto no encontrado"));
    }

    @GetMapping("/product/{id}/sell/{price}")
    public ResponseEntity<?> getMethodName(@PathVariable String id,@PathVariable String price) {
        
        int parsedId = validateInteger(id,"id");
        int parsedPrice = validateInteger(price,"price");

        if( parsedId == -1 || parsedPrice== -1 ){
            return error;
        }

        for (Product product : productList) {
            if (parsedId == product.getId()) {
                product.addSellDetail(new SellDetail(parsedPrice, LocalDateTime.now()));
                return ResponseEntity.ok(product);
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(HttpStatus.NOT_FOUND.value(),"producto no encontrado"));
    }

    private int validateInteger(String intAsStr,String paramName){
        try {
            return Integer.parseInt(intAsStr);
        } catch (Exception e) {
            error = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(HttpStatus.BAD_REQUEST.value(),paramName+" no valido"));
            return -1;
        }
    }
    
}

