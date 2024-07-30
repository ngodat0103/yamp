package com.example.userservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDto {
    private String name;
    private String cityName;
    private String phoneNumber;
    private String province;
    private String district;
    private String ward;
    private String addressType;
}
