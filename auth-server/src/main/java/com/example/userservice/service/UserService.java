package com.example.userservice.service;

import com.example.userservice.dto.model.UserDto;
import com.example.userservice.exceptions.InvalidInputException;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.ArrayList;

@Tag(name = "User",description = "Rest API for user information")
public interface UserService {
    UserDto createUser(UserDto new_user) throws InvalidInputException;
   // Boolean login(String username,String password);
   // ArrayList<UserDto> getAllUser(int page_size, int pageNo, String sortBy, String sortDir);
    UserDto getUserByUsername(String username);
    UserDto updateUser(UserDto update_user,Long user_id);
    void deleteUser(Long user_id);
}
