package org.example.addresssvc.dto.mapper;


import org.example.addresssvc.dto.AddressDto;
import org.example.addresssvc.persistence.document.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract  interface AddressMapper {
    AddressDto toAddressDto(Address address);
    Address toAddress(AddressDto addressDto);
}
