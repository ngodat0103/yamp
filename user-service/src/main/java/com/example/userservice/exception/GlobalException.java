package com.example.userservice.exception;

import com.example.userservice.util.HttpErrorInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.reactive.function.client.WebClientException;
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
    private final ObjectMapper objectMapper;

    public GlobalException(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @ExceptionHandler(ConnectException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public @ResponseBody HttpErrorInfo handleConnectException(HttpServletRequest request){
        return new HttpErrorInfo(HttpStatus.SERVICE_UNAVAILABLE,"Service is not available",request.getRequestURI());
    }


    @ExceptionHandler(WebClientException.class)
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
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody Map<String,String> handleBadRequest(MethodArgumentNotValidException ex){

        List<FieldError> fieldErrors = ex.getFieldErrors();
        Map<String,String> errorMap = new HashMap<>();
        fieldErrors.forEach(f-> errorMap.put(f.getField(),f.getDefaultMessage()));
        return errorMap;
    }

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody HttpErrorInfo handleAccountNotFoundException(AccountNotFoundException ex, HttpServletRequest request){
        return createHttpErrorInfo(HttpStatus.NOT_FOUND,ex,request.getRequestURI());
    }
}
