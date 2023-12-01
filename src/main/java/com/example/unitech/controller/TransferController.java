package com.example.unitech.controller;

import com.example.unitech.dto.transfer.TransferCreateDto;
import com.example.unitech.dto.transfer.TransferResponseDto;
import com.example.unitech.exception.ForbiddenException;
import com.example.unitech.mapper.TransferMapper;
import com.example.unitech.service.auth.jwt.JwtParser;
import com.example.unitech.service.transfer.TransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.unitech.configuration.OpenAPI30Configuration.BEAERER_AUTHENTICATION;
import static com.example.unitech.util.SecurityUtil.extractToken;
import static java.lang.Boolean.FALSE;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

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
            security = @SecurityRequirement(name = BEAERER_AUTHENTICATION))
    @PostMapping
    public TransferResponseDto create(@RequestBody @Valid TransferCreateDto source,
                                      @RequestHeader(AUTHORIZATION) String bearer) {

        val userId = jwtParser.getUserId(extractToken(bearer));
        validate(source.getFromAccountId(), userId);

        return transferMapper.toResponse(transferService.create(source));
    }

    private void validate(Long accountId, Long userId) {
        if (FALSE.equals(transferService.isUserAccount(userId, accountId))) {
            throw new ForbiddenException("it is not your account");
        }
    }
}
