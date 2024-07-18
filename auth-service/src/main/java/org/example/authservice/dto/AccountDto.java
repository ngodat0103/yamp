package org.example.authservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID uuid;
    @NotNull (message = "Username is required")
    private String username;
    @JsonProperty (access = JsonProperty.Access.WRITE_ONLY)
    @NotNull (message = "Password is required")
    @Length (min = 8, message = "Password must be at least 8 characters")
    private String password;
    @Email ( message =  "Email is not valid")
    private String email;

}
