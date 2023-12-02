package com.example.unitech.controller;

import static com.example.unitech.common.Errors.OpenAPI.INVALID_CREDENTIALS;
import static com.example.unitech.common.Errors.OpenAPI.INVALID_REQUEST_BODY;
import static com.example.unitech.configuration.OpenAPI30Configuration.APPLICATION_JSON;

import com.example.unitech.dto.error.ErrorDto;
import com.example.unitech.dto.jwt.TokenDto;
import com.example.unitech.dto.user.UserLoginDto;
import com.example.unitech.service.auth.AuthService;

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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
@Tag(name = "AuthenticationController")
public class AuthenticationController {

    private final AuthService authService;

    @Operation(description = "Login via pin and password")
    @PostMapping("/login")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Ok"),
                @ApiResponse(
                        responseCode = "400",
                        description = INVALID_REQUEST_BODY,
                        content = {
                            @Content(
                                    mediaType = APPLICATION_JSON,
                                    schema = @Schema(implementation = ErrorDto.class))
                        }),
                @ApiResponse(
                        responseCode = "404",
                        description = "User with given pin not found",
                        content = {
                            @Content(
                                    mediaType = APPLICATION_JSON,
                                    schema = @Schema(implementation = ErrorDto.class))
                        }),
                @ApiResponse(
                        responseCode = "403",
                        description = INVALID_CREDENTIALS,
                        content = {
                            @Content(
                                    mediaType = APPLICATION_JSON,
                                    schema = @Schema(implementation = ErrorDto.class))
                        })
            })
    public TokenDto login(@RequestBody @Valid final UserLoginDto source) {
        return authService.login(source);
    }

    // refresh token..
}
