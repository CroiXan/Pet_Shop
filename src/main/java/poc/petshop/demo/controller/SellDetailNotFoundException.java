package poc.petshop.demo.controller;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SellDetailNotFoundException extends RuntimeException{

    public SellDetailNotFoundException(String message){
        super(message);
    }

}
