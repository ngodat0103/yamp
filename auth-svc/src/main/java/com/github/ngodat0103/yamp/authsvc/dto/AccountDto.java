package com.github.ngodat0103.yamp.authsvc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {
    @NotNull (message = "accountUuid is required")
    @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$",
            message = "UUID has to be represented by standard 36-char representation")
    private String accountUuid;
    @NotNull (message = "Username is required")
    private String username;
    @JsonProperty (access = JsonProperty.Access.WRITE_ONLY)
    @NotNull (message = "Password is required")
    @Length (min = 8, message = "Password must be at least 8 characters")
    private String password;
    @Email ( message =  "Email is not valid")
    private String email;
}
