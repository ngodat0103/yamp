package com.example.userservice.dto.model.mapper;

import com.example.userservice.dto.model.UserDto;
import com.example.userservice.entity.Users;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;

@Component
@AllArgsConstructor
public class UserMapper {
    private ModelMapper mapper ;
    public UserDto mapToDto(Users users){
        return mapper.map(users,UserDto.class);
    }
    public Users mapToEntity(UserDto userDto){
        return mapper.map(userDto, Users.class);
    }
}
