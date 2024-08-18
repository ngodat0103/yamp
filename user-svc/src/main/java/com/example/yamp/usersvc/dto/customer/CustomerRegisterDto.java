package com.example.yamp.usersvc.dto.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class CustomerRegisterDto {
    @Email(message = "Email should be valid")
    private String email;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String username;
    @Length(min = 8, message = "Password should be at least 8 characters long")
    private String password;
}
