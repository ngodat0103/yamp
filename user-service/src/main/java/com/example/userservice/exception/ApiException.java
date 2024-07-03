package com.example.userservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class ApiException extends Exception{
    private HttpStatus status;
    private String detail;
}
