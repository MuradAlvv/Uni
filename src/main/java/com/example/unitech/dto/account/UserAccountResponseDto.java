package com.example.unitech.dto.account;

import com.example.unitech.common.AccountStatus;
import com.example.unitech.dto.currency.CurrencyResponseDto;
import com.example.unitech.persistence.entity.CurrencyEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountResponseDto {

    private Long id;
    private BigDecimal balance;
    private CurrencyResponseDto currency;
    private AccountStatus status;
    private LocalDateTime createdAt;

    public UserAccountResponseDto(
            Long id,
            BigDecimal balance,
            CurrencyEntity currency,
            AccountStatus status,
            LocalDateTime createdAt) {
        this.id = id;
        this.balance = balance;
        this.currency =
                CurrencyResponseDto.builder()
                        .setCode(currency.getCode())
                        .setId(currency.getId())
                        .setName(currency.getName())
                        .setSymbol(currency.getSymbol())
                        .build();
        this.status = status;
        this.createdAt = createdAt;
    }
}
