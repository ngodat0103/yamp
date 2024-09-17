package com.github.ngodat0103.yamp.authsvc.dto.account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
@Getter
@Builder
@EqualsAndHashCode
public class AccountResponseDto {
    @NotNull(message = "accountUuid is required")
    @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$",
            message = "UUID has to be represented by standard 36-char representation")
    private String uuid;
    @NotNull (message = "Username is required")
    @Length(min = 5, message = "Username must be at least 5 characters")
    private String username;
    @NotNull (message = "Password is required")
    @NotNull (message = "Email is required")
    @Email( message =  "Email is not valid")
    private String email;
    @NotNull (message = "Role name is required")
    private String roleName;
}
