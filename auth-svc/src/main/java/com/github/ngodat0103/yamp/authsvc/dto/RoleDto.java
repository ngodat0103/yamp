package com.github.ngodat0103.yamp.authsvc.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoleDto {
    private String RoleName;
    private String roleDescription;
}
