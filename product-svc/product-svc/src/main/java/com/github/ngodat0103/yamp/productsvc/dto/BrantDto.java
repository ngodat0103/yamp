package com.github.ngodat0103.yamp.productsvc.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class BrantDto {
    private UUID brandUuid;
    private String name;
    private String logo;
}
