package com.example.unitech.dto.account;

import com.example.unitech.common.AccountStatus;
import com.example.unitech.dto.currency.CurrencyResponseDto;
import com.example.unitech.dto.user.UserResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDetailedResponseDto {
    private Long id;
    private BigDecimal balance;
    private CurrencyResponseDto currency;
    private UserResponseDto user;
    private AccountStatus status;
    private LocalDateTime createdAt;
}
