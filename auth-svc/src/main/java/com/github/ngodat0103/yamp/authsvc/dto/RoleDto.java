package com.github.ngodat0103.yamp.authsvc.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class RoleDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID roleUuid;
    private String roleName;
    private String roleDescription;
}
