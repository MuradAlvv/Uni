package com.example.unitech.controller;

import com.example.unitech.dto.jwt.TokenDto;
import com.example.unitech.dto.user.UserLoginDto;
import com.example.unitech.service.auth.AuthService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthenticationController {

    private final AuthService authService;

    @PostMapping("/login")
    public TokenDto login(@RequestBody @Valid final UserLoginDto source) {
        return authService.login(source);
    }
}
