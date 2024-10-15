package com.example.yamp.usersvc.dto.mapper;

import com.example.yamp.usersvc.dto.SiteUserDto;
import com.example.yamp.usersvc.persistence.entity.SiteUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface SiteUserMapper {

  SiteUserDto toDto(SiteUser siteUser);

  SiteUser toEntity(SiteUserDto siteUserDto);
}
