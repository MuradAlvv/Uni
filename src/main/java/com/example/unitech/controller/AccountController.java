package com.example.unitech.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.example.unitech.dto.account.AccountCreateDto;
import com.example.unitech.dto.account.AccountCreatedResponseDto;
import com.example.unitech.dto.account.UserAccountResponseDto;
import com.example.unitech.mapper.AccountMapper;
import com.example.unitech.service.account.AccountService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/accounts")
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @PostMapping
    @ResponseStatus(CREATED)
    public AccountCreatedResponseDto create(@RequestBody @Valid final AccountCreateDto source) {
        return accountMapper.toResponse(accountService.create(source));
    }

    @GetMapping("/users/{userId}")
    public List<UserAccountResponseDto> getByUserId(@PathVariable final Long userId) {
        return accountService.getByUserId(userId);
    }
}
