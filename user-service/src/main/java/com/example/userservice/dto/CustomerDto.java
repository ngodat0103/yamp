package com.example.userservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CustomerDto {
    @JsonIgnore
    private UUID accountUuid;
    @NotNull(message = "First name should not be null")
    private String firstName;
    @NotNull(message = "Last name should not be null")
    private String lastName;
    @Email(message = "Email should be valid")
    private String email;
    @Null
    private String phoneNumber;
    private String username;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Null
    private Set<AddressDto> addresses;
}
