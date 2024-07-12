package com.example.userservice.dto.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoleDto {
    @Nullable
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long roleID;
    @NotNull
    private String roleName;
    @NotNull
    private String roleDescription;
    @NotNull
    private Long userId;
}
