package com.example.unitech.service.auth;

import com.example.unitech.dto.jwt.TokenDto;
import com.example.unitech.dto.user.UserLoginDto;

public interface AuthService {

    TokenDto login(UserLoginDto userLoginDto);
}
