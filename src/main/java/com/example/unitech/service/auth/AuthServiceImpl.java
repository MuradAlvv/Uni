package com.example.unitech.service.auth;

import com.example.unitech.dto.jwt.TokenDto;
import com.example.unitech.dto.user.UserLoginDto;

import com.example.unitech.service.auth.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    @Override
    public TokenDto login(UserLoginDto userLoginDto) {
        val token =
                new UsernamePasswordAuthenticationToken(
                        userLoginDto.getPin(), userLoginDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new TokenDto(jwtProvider.generateToken(authentication));
    }
}
