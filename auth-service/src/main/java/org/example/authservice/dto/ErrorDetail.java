package org.example.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import java.util.Date;
@AllArgsConstructor
@Getter
public class ErrorDetail {
    private Date timeStamp;
    private HttpStatus status;
    private String message;
    private String path;
}
