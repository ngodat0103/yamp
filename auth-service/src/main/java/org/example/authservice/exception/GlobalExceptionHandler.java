package org.example.authservice.exception;

import org.example.authservice.dto.ErrorDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorDetail> handleApiException(ApiException e, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail(new Date(), e.getStatus(), e.getMessage(), request.getContextPath());
        return ResponseEntity.status(e.getStatus()).body(errorDetail);
    }


}
