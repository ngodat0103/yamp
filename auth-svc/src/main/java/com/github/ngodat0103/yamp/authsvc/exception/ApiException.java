package com.github.ngodat0103.yamp.authsvc.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class ApiException extends RuntimeException{
    private final HttpStatus status;
    private final String message;

}
