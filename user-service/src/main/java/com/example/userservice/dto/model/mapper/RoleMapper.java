package com.example.userservice.dto.model.mapper;


import com.example.userservice.dto.model.RoleDto;
import com.example.userservice.entity.Role;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RoleMapper {
    ModelMapper modelMapper;
    public Role modelToEntity(RoleDto roleDto) {
        return modelMapper.map(roleDto, Role.class);
    }
    public RoleDto entityToDto(Role role) {
        return modelMapper.map(role, RoleDto.class);
    }
}
