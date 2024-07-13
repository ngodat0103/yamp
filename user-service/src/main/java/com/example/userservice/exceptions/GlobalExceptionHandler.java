package com.example.userservice.exceptions;
import com.example.userservice.util.HttpErrorInfo;
import io.jsonwebtoken.JwtException;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import jakarta.ws.rs.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final HttpSecurity httpSecurity;

    public GlobalExceptionHandler(HttpSecurity httpSecurity) {
        this.httpSecurity = httpSecurity;
    }

    private HttpErrorInfo createHttpErrorInfo(HttpStatus httpStatus, Exception ex){
        final String message = ex.getMessage();
        return new HttpErrorInfo(httpStatus,message);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InvalidInputException.class)
    public @ResponseBody HttpErrorInfo handleInvalidInputException(Exception ex){
        return createHttpErrorInfo(HttpStatus.UNPROCESSABLE_ENTITY,ex);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody Map<String,String> handleBadRequest(MethodArgumentNotValidException ex){

        List<FieldError> fieldErrors = ex.getFieldErrors();
        Map<String,String> errorMap = new HashMap<>();

        for(FieldError field : fieldErrors){
            errorMap.put(field.getField(),field.getDefaultMessage());
        }
        return errorMap;
    }
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody HttpErrorInfo handleNotFound(Exception ex){
        return createHttpErrorInfo(HttpStatus.NOT_FOUND,ex);
    }

    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public @ResponseBody HttpErrorInfo handleBadCredential(Exception ex){
        return createHttpErrorInfo(HttpStatus.UNAUTHORIZED,ex);
    }




}
