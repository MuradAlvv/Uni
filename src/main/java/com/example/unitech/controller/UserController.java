package com.example.unitech.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.example.unitech.dto.user.UserCreateDto;
import com.example.unitech.dto.user.UserResponseDto;
import com.example.unitech.mapper.UserMapper;
import com.example.unitech.service.user.UserService;

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
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    @ResponseStatus(CREATED)
    public UserResponseDto create(@RequestBody @Valid final UserCreateDto source) {
        return userMapper.toResponse(userService.create(source));
    }
}
