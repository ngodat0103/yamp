package com.github.ngodat0103.yamp.authsvc.dto.account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Builder
@EqualsAndHashCode
public class AccountRegisterDto {
  @NotNull(message = "Username is required")
  @Length(min = 5, message = "Username must be at least 5 characters")
  private String username;
  @NotNull(message = "Password is required")
  @Length(min = 8, message = "Password must be at least 8 characters")
  private String password;
  @NotNull(message = "Email is required")
  @Email(message = "Email is not valid")
  private String email;

  @NotNull(message = "First name is required")
  @Length(min = 2,max = 10)
  private String firstName;
  @NotNull(message = "Last name is required")
  @Length(min = 2,max = 10)
  private String lastName;
}
