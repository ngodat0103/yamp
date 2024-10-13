package com.example.yamp.usersvc.dto.mapper;

import com.example.yamp.usersvc.dto.address.AddressDto;
import com.example.yamp.usersvc.persistence.entity.Address;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface AddressMapper {
  Set<AddressDto> mapToDtos(Set<Address> addresses);

  @Mapping(target = "customerUuid", ignore = true)
  Address mapToEntity(AddressDto addressDto);
}
