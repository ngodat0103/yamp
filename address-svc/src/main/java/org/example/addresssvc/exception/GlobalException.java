package org.example.addresssvc.exception;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException  {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(GlobalException.class);

   @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
   public Mono<Map<String,String>> handleWebExchangeBindException(WebExchangeBindException e) {
       Map<String, String> errors = new HashMap<>();
       e.getFieldErrors().forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));
       return Mono.just(errors);
   }

    @ExceptionHandler(WebClientResponseException.InternalServerError.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<Void> handleWebClientResponseException(){
        return Mono.empty();
    }

    @ExceptionHandler(AddressConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Mono<Map<String,String>> handleAddressConflict(AddressConflictException e){
        Map<String, String> errors = new HashMap<>();
        errors.put("message", e.getMessage());
        return Mono.just(errors);
    }


    @ExceptionHandler(AddressNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<Map<String,String>> handleAddressNotFound(AddressNotFoundException e){
        Map<String, String> errors = new HashMap<>();
        errors.put("message", e.getMessage());
        return Mono.just(errors);
    }

}
