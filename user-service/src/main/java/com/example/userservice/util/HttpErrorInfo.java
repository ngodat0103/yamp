package com.example.userservice.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
@Getter
@Setter
public class HttpErrorInfo {
    private final ZonedDateTime timestamp;
    private final HttpStatus httpStatus;
    private final String message;
    public HttpErrorInfo(HttpStatus httpStatus,String message){
        this.httpStatus = httpStatus;
        this.message = message ;
        this.timestamp = ZonedDateTime.now();
    }

}
