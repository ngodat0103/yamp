package com.example.userservice.exception;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.util.Objects;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(RestClientException.class)
    public void handleRestClientException(HttpClientErrorException e, HttpServletResponse response) throws IOException {
        response.setStatus(e.getStatusCode().value());
        response.setContentType("application/json");
        response.getWriter().write(Objects.requireNonNull(e.getResponseBodyAsString()));
    }
}
