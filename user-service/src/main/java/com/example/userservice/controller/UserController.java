package com.example.userservice.controller;

import com.example.userservice.dto.model.UserDto;
import com.example.userservice.exceptions.InvalidInputException;
import com.example.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;
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
    @PostMapping(produces = "application/json")
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto)
            throws InvalidInputException
    {
            return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
    }



}
