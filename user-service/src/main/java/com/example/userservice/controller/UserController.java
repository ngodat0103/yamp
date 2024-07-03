package com.example.userservice.controller;

import com.example.userservice.dto.model.UserDto;
import com.example.userservice.exception.ApiException;
import com.example.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private UserService userService;
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto) throws ApiException {
        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
    }



}
