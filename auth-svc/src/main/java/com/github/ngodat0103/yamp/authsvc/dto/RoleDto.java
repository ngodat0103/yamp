package com.github.ngodat0103.yamp.authsvc.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class RoleDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID uuid;
    private String roleName;
    private String roleDescription;
}
