package com.example.userservice.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
@Getter
@Setter
public class HttpErrorInfo {
    private final String timestamp;
    private final HttpStatus status;
    private final String message;
    private String path;
    public HttpErrorInfo(HttpStatus httpStatus, String message, String path){
        this.status = httpStatus;
        this.message = message;
        this.timestamp = ZonedDateTime.now().toString();
        this.path = path ;
    }

}
