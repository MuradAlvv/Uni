package com.example.unitech.controller;

import static com.example.unitech.common.Errors.OpenAPI.INVALID_BEARER;
import static com.example.unitech.common.Errors.OpenAPI.INVALID_REQUEST_BODY;
import static com.example.unitech.configuration.OpenAPI30Configuration.APPLICATION_JSON;
import static com.example.unitech.configuration.OpenAPI30Configuration.BEARER_AUTHENTICATION;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.CREATED;

import static java.lang.Boolean.FALSE;

import com.example.unitech.dto.error.ErrorDto;
import com.example.unitech.dto.transfer.TransferCreateDto;
import com.example.unitech.dto.transfer.TransferResponseDto;
import com.example.unitech.exception.ForbiddenException;
import com.example.unitech.mapper.TransferMapper;
import com.example.unitech.service.auth.jwt.JwtParser;
import com.example.unitech.service.transfer.TransferService;

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

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/transfers")
@Tag(name = "TransferController")
public class TransferController {

    private final TransferService transferService;
    private final TransferMapper transferMapper;
    private final JwtParser jwtParser;

    @Operation(
            description = "Transfer money to another account",
            security = @SecurityRequirement(name = BEARER_AUTHENTICATION))
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "201", description = "Transfer succeeded"),
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
                        }),
                @ApiResponse(
                        responseCode = "404",
                        description = "Account with given id not found",
                        content = {
                            @Content(
                                    mediaType = APPLICATION_JSON,
                                    schema = @Schema(implementation = ErrorDto.class))
                        })
            })
    @PostMapping
    @ResponseStatus(CREATED)
    public TransferResponseDto create(
            @RequestBody @Valid TransferCreateDto source,
            @RequestHeader(value = AUTHORIZATION, required = false) String bearer) {
        val userId = jwtParser.getUserIdFromBearer(bearer);
        validate(source.getFromAccountId(), userId);

        return transferMapper.toResponse(transferService.create(source));
    }

    private void validate(Long accountId, Long userId) {
        if (FALSE == transferService.isUserAccount(userId, accountId)) {
            throw new ForbiddenException("Forbidden");
        }
    }
}
