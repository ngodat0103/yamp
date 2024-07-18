package org.example.authservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class ApiException extends RuntimeException{
    private HttpStatus status;
    private String message;

}
