package com.example.userservice.exception;

import com.example.userservice.util.HttpErrorInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import javax.security.auth.login.AccountNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class GlobalException {

    private final Logger logger = LoggerFactory.getLogger(GlobalException.class);
    private final ObjectMapper objectMapper;

    public GlobalException(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @ExceptionHandler(value = {WebClientRequestException.class, OAuth2AuthorizationException.class})
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public @ResponseBody HttpErrorInfo handleConnectException(HttpServletRequest request, Exception e){
        if( e instanceof WebClientRequestException) {
            logger.error("auth service is not available");
        }
        else if(e instanceof OAuth2AuthorizationException)
            logger.error(e.getMessage());


        return new HttpErrorInfo(HttpStatus.SERVICE_UNAVAILABLE,"Service is not available",request.getRequestURI());
    }


    @ExceptionHandler(WebClientResponseException.class)
    public void handleRestClientException(WebClientResponseException e, HttpServletResponse rp, HttpServletRequest rq) throws IOException {
        rp.setStatus(e.getStatusCode().value());
        rp.setContentType("application/json");
        HttpErrorInfo httpErrorInfo = this.objectMapper.readValue(e.getResponseBodyAsString(), HttpErrorInfo.class);
        httpErrorInfo.setPath(rq.getRequestURI());
        PrintWriter printWriter = rp.getWriter();
        printWriter.write(this.objectMapper.writeValueAsString(httpErrorInfo));
    }
    private HttpErrorInfo createHttpErrorInfo(HttpStatus httpStatus, @Nullable Exception ex, String path){
        assert ex != null;
        final String message = ex.getMessage();
        return new HttpErrorInfo(httpStatus,message,path);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InvalidInputException.class)
    public @ResponseBody HttpErrorInfo handleInvalidInputException(Exception ex, HttpServletRequest request){
        return createHttpErrorInfo(HttpStatus.UNPROCESSABLE_ENTITY,ex,request.getRequestURI());
    }
    @ExceptionHandler({MethodArgumentNotValidException.class, MissingRequestValueException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody Map<String,String> handleBadRequest(Exception ex){

       if(ex instanceof MethodArgumentNotValidException methodArgumentNotValidException){
           List<FieldError> fieldErrors = methodArgumentNotValidException.getFieldErrors();
           Map<String,String> errorMap = new HashMap<>();
           fieldErrors.forEach(f-> errorMap.put(f.getField(),f.getDefaultMessage()));
           return errorMap;
       }
         else if(ex instanceof MissingRequestValueException missingRequestValueException){
                Map<String,String> errorMap = new HashMap<>();
                errorMap.put("message",missingRequestValueException.getMessage());
              return errorMap;
         }
         return null;
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody HttpErrorInfo handleAccountNotFoundException(NotFoundException ex, HttpServletRequest request){
        return createHttpErrorInfo(HttpStatus.NOT_FOUND,ex,request.getRequestURI());
    }


    @ExceptionHandler(AddressNameConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public HttpErrorInfo handleAddressNameConflictException(AddressNameConflictException ex, HttpServletRequest request){
        return createHttpErrorInfo(HttpStatus.CONFLICT,ex,request.getRequestURI());
    }
}
