package com.github.ngodat0103.yamp.authsvc.event;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class Model {
    private String uuid;
    private LocalDateTime createAt;
    private LocalDateTime lastModifiedAt;
}
