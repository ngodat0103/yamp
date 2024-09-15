package com.github.ngodat0103.yamp.authsvc.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
public class UpdateAccountDto {
    @Length(min = 5,message = "Username must be at least 5 characters")
    @NotNull
    private String username;
    @Email(message = "Email is not valid")
    @NotNull(message = "email is required")
    private String email;
    @NotNull(message = "roleName is required")
    private String roleName;
}
