package com.example.unitech.controller;

import static com.example.unitech.common.Errors.OpenAPI.INVALID_REQUEST_BODY;
import static com.example.unitech.configuration.OpenAPI30Configuration.APPLICATION_JSON;

import static org.springframework.http.HttpStatus.CREATED;

import com.example.unitech.dto.error.ErrorDto;
import com.example.unitech.dto.user.UserCreateDto;
import com.example.unitech.dto.user.UserResponseDto;
import com.example.unitech.mapper.UserMapper;
import com.example.unitech.service.user.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "UserController")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(description = "Register via Pin")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "201", description = "User created"),
                @ApiResponse(
                        responseCode = "400",
                        description = INVALID_REQUEST_BODY,
                        content = {
                            @Content(
                                    mediaType = APPLICATION_JSON,
                                    schema = @Schema(implementation = ErrorDto.class))
                        })
            })
    @PostMapping
    @ResponseStatus(CREATED)
    public UserResponseDto create(@RequestBody @Valid final UserCreateDto source) {
        return userMapper.toResponse(userService.create(source));
    }
}
