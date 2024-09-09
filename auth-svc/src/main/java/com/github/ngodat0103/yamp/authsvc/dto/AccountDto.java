package com.github.ngodat0103.yamp.authsvc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {
    @NotNull (message = "accountUuid is required")
    @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$",
            message = "UUID has to be represented by standard 36-char representation")
    private String uuid;
    @NotNull (message = "Username is required")
    @Length (min = 5, message = "Username must be at least 5 characters")
    private String username;
    @JsonProperty (access = JsonProperty.Access.WRITE_ONLY)
    @NotNull (message = "Password is required")
    @Length (min = 8, message = "Password must be at least 8 characters")
    private String password;
    @NotNull (message = "Email is required")
    @Email ( message =  "Email is not valid")
    private String email;
    private String roleName;

}
