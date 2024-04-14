package poc.petshop.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import poc.petshop.demo.model.ErrorMessage;
import poc.petshop.demo.model.SellDetail;
import poc.petshop.demo.service.ProductService;
import poc.petshop.demo.service.SellDetailService;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/review")
public class SellDetailController {

    @Autowired
    private ProductService productService;

    @Autowired
    private SellDetailService sellDetailService;

    @GetMapping
    public ResponseEntity<List<SellDetail>> getAllSellDetail() {
        return ResponseEntity.ok(sellDetailService.getSellDetails());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getAllSellDetailById(@PathVariable String id) {

        Long parsedId = validateInteger(id,"id");
        Optional<SellDetail> sellDetail = sellDetailService.getSellDetailById(parsedId);

        if (sellDetail.isEmpty()) {
            return buildResponseError(HttpStatus.NOT_FOUND,"producto no encontrado");
        }

        return ResponseEntity.ok(sellDetail);

    }

    @PostMapping
    public ResponseEntity<?> addSellDetail(@RequestBody SellDetail sellDetail) {
        
        if(sellDetail.getId() < 0L){
            return buildResponseError(HttpStatus.BAD_REQUEST,"id no puede ser un valor negativo");
        }

        if(sellDetail.getIdProduct() < 0L){
            return buildResponseError(HttpStatus.BAD_REQUEST,"id de producto no puede ser un valor negativo");
        }

        if (sellDetail.getSellPrice() < 0) {
            return buildResponseError(HttpStatus.BAD_REQUEST,"SellPrice no puede ser un valor negativo.");
        }

        if (productService.existsProductById(sellDetail.getIdProduct())) {
            return buildResponseError(HttpStatus.NOT_FOUND,"producto no encontrado");
        }

        return ResponseEntity.ok(sellDetailService.createSellDetail(sellDetail));
    }
    
    @PutMapping
    public ResponseEntity<?> updateSellDetail(@RequestBody SellDetail sellDetail) {
        
        if(sellDetail.getId() < 0L){
            return buildResponseError(HttpStatus.BAD_REQUEST,"id no puede ser un valor negativo");
        }

        if(sellDetail.getIdProduct() < 0L){
            return buildResponseError(HttpStatus.BAD_REQUEST,"id de producto no puede ser un valor negativo");
        }

        if (sellDetail.getSellPrice() < 0) {
            return buildResponseError(HttpStatus.BAD_REQUEST,"SellPrice no puede ser un valor negativo.");
        }

        if (productService.existsProductById(sellDetail.getIdProduct())) {
            return buildResponseError(HttpStatus.NOT_FOUND,"producto no encontrado");
        }

        if (sellDetailService.existsSellDetailById(sellDetail.getId())) {
            return buildResponseError(HttpStatus.NOT_FOUND,"venta no encontrada");
        }

        return ResponseEntity.ok(sellDetailService.createSellDetail(sellDetail));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteSellDetail(@PathVariable String id){
        
        Long parsedId = validateInteger(id, "id");

        if (sellDetailService.existsSellDetailById(parsedId)) {
            return buildResponseError(HttpStatus.NOT_FOUND,"venta no encontrada");
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
    
    private ResponseEntity<ErrorMessage> buildResponseError(HttpStatus status,String message){
        return ResponseEntity.status(status).body(new ErrorMessage(status.value(),message));
    }
}
