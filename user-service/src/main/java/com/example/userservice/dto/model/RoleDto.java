package com.example.userservice.dto.model;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoleDto {
    @Nullable
    private Long roleID;
    @NotNull
    private String roleName;
    @NotNull
    private String roleDescription;
}
