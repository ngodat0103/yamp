package com.example.yamp.usersvc.dto.customer;

import com.example.yamp.usersvc.dto.address.AddressDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerDto {
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private UUID accountUuid;
  AccountDto account;

  @NotNull(message = "First name should not be null")
  private String firstName;

  @NotNull(message = "Last name should not be null")
  private String lastName;

  @Email(message = "Email should be valid")
  @Null
  private String phoneNumber;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @Null
  private Set<AddressDto> addresses;
}
