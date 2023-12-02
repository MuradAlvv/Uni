package com.example.unitech.controller;

import static com.example.unitech.common.Errors.OpenAPI.INVALID_BEARER;
import static com.example.unitech.common.Errors.OpenAPI.INVALID_REQUEST_BODY;
import static com.example.unitech.configuration.OpenAPI30Configuration.APPLICATION_JSON;
import static com.example.unitech.configuration.OpenAPI30Configuration.BEARER_AUTHENTICATION;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.CREATED;

import static java.lang.Boolean.FALSE;

import com.example.unitech.dto.account.AccountCreateDto;
import com.example.unitech.dto.account.AccountDetailedResponseDto;
import com.example.unitech.dto.account.UserAccountResponseDto;
import com.example.unitech.dto.error.ErrorDto;
import com.example.unitech.exception.ForbiddenException;
import com.example.unitech.mapper.AccountMapper;
import com.example.unitech.service.account.AccountService;
import com.example.unitech.service.auth.jwt.JwtParser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.val;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
    private final JwtParser jwtParser;

    @Operation(
            description = "Create account",
            security = @SecurityRequirement(name = BEARER_AUTHENTICATION))
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "201", description = "Account created"),
                @ApiResponse(
                        responseCode = "400",
                        description = INVALID_REQUEST_BODY,
                        content = {
                            @Content(
                                    mediaType = APPLICATION_JSON,
                                    schema = @Schema(implementation = ErrorDto.class))
                        }),
                @ApiResponse(
                        responseCode = "403",
                        description = INVALID_BEARER,
                        content = {
                            @Content(
                                    mediaType = APPLICATION_JSON,
                                    schema = @Schema(implementation = ErrorDto.class))
                        })
            })
    @PostMapping
    @ResponseStatus(CREATED)
    public AccountDetailedResponseDto create(
            @RequestBody @Valid final AccountCreateDto source,
            @RequestHeader(value = AUTHORIZATION, required = false) final String bearer) {
        val userId = jwtParser.getUserIdFromBearer(bearer);
        source.setUserId(userId);

        return accountMapper.toDetailedResponse(accountService.create(source));
    }

    @Operation(
            description = "List user accounts",
            security = @SecurityRequirement(name = BEARER_AUTHENTICATION))
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Ok",
                        content = {@Content(mediaType = APPLICATION_JSON)}),
                @ApiResponse(
                        responseCode = "400",
                        description = INVALID_REQUEST_BODY,
                        content = {
                            @Content(
                                    mediaType = APPLICATION_JSON,
                                    schema = @Schema(implementation = ErrorDto.class))
                        }),
                @ApiResponse(
                        responseCode = "403",
                        description = INVALID_BEARER,
                        content = {
                            @Content(
                                    mediaType = APPLICATION_JSON,
                                    schema = @Schema(implementation = ErrorDto.class))
                        })
            })
    @GetMapping("/users/{userId}")
    public List<UserAccountResponseDto> getByUserId(
            @PathVariable final Long userId,
            @RequestHeader(value = AUTHORIZATION, required = false) final String bearer) {
        if (FALSE == jwtParser.getUserIdFromBearer(bearer).equals(userId)) {
            throw new ForbiddenException("Forbidden");
        }
        return accountService.getByUserId(userId);
    }
}
