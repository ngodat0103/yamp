package com.example.yamp.usersvc.dto.mapper;


import com.example.yamp.usersvc.dto.address.AddressDto;
import com.example.yamp.usersvc.persistence.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Set<AddressDto> mapToDtos(Set<Address> addresses);

    @Mapping(target = "customerUuid",ignore = true)
    Address mapToEntity(AddressDto addressDto);
}
