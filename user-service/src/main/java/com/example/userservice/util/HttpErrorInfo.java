package com.example.userservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
@Getter
@Setter
public class HttpErrorInfo {
    private final String timestamp;
    private final String httpStatus;
    private final String message;
    private final String path;
//    public HttpErrorInfo(HttpStatus httpStatus, String message, HttpServletRequest request){
//        this.httpStatus = httpStatus.toString();
//        this.message = message ;
//        this.timestamp = ZonedDateTime.now().toString();
//        this.path = request.getServletPath();
//    }
    public HttpErrorInfo(HttpStatus httpStatus,String message){
        this.httpStatus = httpStatus.toString();
        this.message = message;
        this.timestamp = ZonedDateTime.now().toString();
        this.path = null ;
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }

}
