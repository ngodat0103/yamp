package com.example.userservice.service.impl;

import com.example.userservice.dto.model.UserDto;
import com.example.userservice.dto.model.mapper.UserMapper;
import com.example.userservice.entity.Users;
import com.example.userservice.exceptions.InvalidInputException;
import com.example.userservice.repositories.UserRepository;
import com.example.userservice.service.UserService;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDto createUser(UserDto userDto) throws InvalidInputException {
        if(userRepository.existsByUsername(userDto.getUsername()))
            throw new InvalidInputException("Username is already exists");
        if(userRepository.existsByEmail(userDto.getEmail()))
            throw new InvalidInputException("Email is already exist");

        Users newUsers = this.userMapper.mapToEntity(userDto);
        newUsers.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Users usersResponse = userRepository.save(newUsers);

        return userMapper.mapToDto(usersResponse);

    }

//    @Override
//    public ArrayList<UserDto> getAllUser(int page_size, int pageNo, String sortBy, String sortDir) {
//
//    }

    @Override
    public UserDto getUserByUsername(String username) throws NotFoundException {
        if(!userRepository.existsByUsername(username)){
            throw new NotFoundException("Username not found");
        }
        Users user = userRepository.findByUsername(username);
        return userMapper.mapToDto(user);
    }

    @Override
    public UserDto updateUser(UserDto update_user, Long user_id) {
        return null;
    }

    @Override
    public void deleteUser(Long user_id) {

    }
}
