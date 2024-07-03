package com.example.userservice.service;

import com.example.userservice.dto.model.UserDto;
import com.example.userservice.exception.ApiException;

public interface UserService {
    UserDto createUser(UserDto new_user) throws ApiException;
    UserDto getAllUser(int page_size,int pageNo,String sortBy,String sortDir);
    UserDto getUserByUsername(String username);
    UserDto updateUser(UserDto update_user,Long user_id);
    void deleteUser(Long user_id);
}
