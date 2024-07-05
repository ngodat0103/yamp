package com.example.userservice.controller;

import com.example.userservice.dto.model.UserDto;
import com.example.userservice.entity.Users;
import com.example.userservice.exceptions.InvalidInputException;
import com.example.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Operation(
            summary =
                    "${api.create-user.summary}",
            description =
                    "${api.create-user.description}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =
                    "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "400", description =
                    "${api.responseCodes.badRequest.description}"),
            @ApiResponse(responseCode = "404", description =
                    "${api.responseCodes.notFound.description}"),
            @ApiResponse(responseCode = "422", description =
                    "${api.responseCodes.unprocessableEntity.description}")}
    )
    @PostMapping(produces = "application/json",path = "/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody @Valid UserDto userDto)
            throws InvalidInputException
    {
        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
    }
    @ResponseBody
    @GetMapping(produces = "application/json",path = "/login")
    public String loginUser(@RequestParam String username ,@RequestParam String password){
        UserDto currentUser = userService.getUserByUsername(username);

        if( passwordEncoder.matches(password,currentUser.getPassword())){
            return "Ok";
        }
        else return "Incorrect password";

    }



}
