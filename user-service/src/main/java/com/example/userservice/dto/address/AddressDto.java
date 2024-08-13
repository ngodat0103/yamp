package com.example.userservice.dto.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;


@Data
@Builder
public class AddressDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID uuid;
    @NotNull
    private String name;
    @NotNull
    private String cityName;
    @NotNull
    private String phoneNumber;
    @NotNull
    @NotNull
    private String province;

    @NotNull
    private String street;

    @NotNull
    private String ward;
    @NotNull
    private String district;

    @NotNull
    private String addressType;
}
