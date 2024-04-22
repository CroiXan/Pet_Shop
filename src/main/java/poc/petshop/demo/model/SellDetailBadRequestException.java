package poc.petshop.demo.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SellDetailBadRequestException extends RuntimeException{

    public SellDetailBadRequestException(String message){
        super(message);
    }

}
