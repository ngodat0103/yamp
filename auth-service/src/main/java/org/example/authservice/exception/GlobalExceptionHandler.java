package org.example.authservice.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.example.authservice.dto.ErrorDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler  {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorDetail> handleApiException(ApiException e, HttpServletRequest request) {
        ErrorDetail errorDetail = new ErrorDetail(new Date(), e.getStatus(), e.getMessage(),request.getServletPath());
        return ResponseEntity.status(e.getStatus()).body(errorDetail);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        return new ErrorDetail(new Date(), HttpStatus.BAD_REQUEST, Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage(),request.getServletPath());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDetail handleHttpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request) {
        return new ErrorDetail(new Date(), HttpStatus.BAD_REQUEST, "Malformed JSON request",request.getServletPath());
    }

    @ExceptionHandler(ServletException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDetail handleServletException(ServletException e, HttpServletRequest request) {
        return new ErrorDetail(new Date(), HttpStatus.BAD_REQUEST, e.getMessage(),request.getServletPath());
    }


}
