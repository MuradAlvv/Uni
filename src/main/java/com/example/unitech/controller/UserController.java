package com.example.unitech.controller;

import com.example.unitech.dto.UserRequestDto;
import com.example.unitech.dto.UserResponseDto;
import com.example.unitech.mapper.UserMapper;
import com.example.unitech.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    @ResponseStatus(CREATED)
    public UserResponseDto create(@RequestBody final UserRequestDto source) {
        val user = userMapper.toEntity(source);

        return userMapper.toResponse(userService.create(user));
    }
}
