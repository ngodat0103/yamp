package com.example.userservice.dto.model.mapper;

import com.example.userservice.dto.model.UserDto;
import com.example.userservice.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;

@Component
@AllArgsConstructor
public class UserMapper {
    private ModelMapper mapper ;
    public UserDto mapToDto(User user){
        return mapper.map(user,UserDto.class);
    }
    public User mapToEntity(UserDto userDto){
        return mapper.map(userDto, User.class);
    }
}
