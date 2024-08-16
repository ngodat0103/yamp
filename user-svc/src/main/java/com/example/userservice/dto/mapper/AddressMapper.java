package com.example.userservice.dto.mapper;


import com.example.userservice.dto.address.AddressDto;
import com.example.userservice.persistence.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Set<AddressDto> mapToDtos(Set<Address> addresses);

    @Mapping(target = "customer",ignore = true)
    Address mapToEntity(AddressDto addressDto);
}
