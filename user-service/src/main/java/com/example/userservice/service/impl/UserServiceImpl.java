package com.example.userservice.service.impl;

import com.example.userservice.dto.model.UserDto;
import com.example.userservice.dto.model.mapper.UserMapper;
import com.example.userservice.entity.Users;
import com.example.userservice.exceptions.InvalidInputException;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public UserDto createUser(UserDto userDto) throws InvalidInputException {
        if(userRepository.existsByUsername(userDto.getUsername()))
            throw new InvalidInputException("Username is already exists");
        if(userRepository.existsByEmail(userDto.getEmail()))
            throw new InvalidInputException("Email is already exist");

        Users newUsers = this.userMapper.mapToEntity(userDto);
        Users usersResponse = userRepository.save(newUsers);

        return userMapper.mapToDto(usersResponse);

    }

    @Override
    public UserDto getAllUser(int page_size, int pageNo, String sortBy, String sortDir) {
        return null;
    }

    @Override
    public UserDto getUserByUsername(String username) {
        return null;
    }

    @Override
    public UserDto updateUser(UserDto update_user, Long user_id) {
        return null;
    }

    @Override
    public void deleteUser(Long user_id) {

    }
}
