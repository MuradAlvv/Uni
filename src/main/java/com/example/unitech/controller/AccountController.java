package com.example.unitech.controller;

import static com.example.unitech.configuration.OpenAPI30Configuration.BEARER_AUTHENTICATION;

import static org.springframework.http.HttpStatus.CREATED;

import com.example.unitech.dto.account.AccountCreateDto;
import com.example.unitech.dto.account.AccountDetailedResponseDto;
import com.example.unitech.dto.account.UserAccountResponseDto;
import com.example.unitech.mapper.AccountMapper;
import com.example.unitech.service.account.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

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
@Tag(name = "AccountController")
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @Operation(
            description = "Create account",
            security = @SecurityRequirement(name = BEARER_AUTHENTICATION))
    @PostMapping
    @ResponseStatus(CREATED)
    public AccountDetailedResponseDto create(@RequestBody @Valid final AccountCreateDto source) {
        return accountMapper.toDetailedResponse(accountService.create(source));
    }

    @Operation(
            description = "List user accounts",
            security = @SecurityRequirement(name = BEARER_AUTHENTICATION))
    @GetMapping("/users/{userId}")
    public List<UserAccountResponseDto> getByUserId(@PathVariable final Long userId) {
        return accountService.getByUserId(userId);
    }
}
