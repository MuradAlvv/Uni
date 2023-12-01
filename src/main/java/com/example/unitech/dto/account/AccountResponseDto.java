package com.example.unitech.dto.account;

import com.example.unitech.common.AccountStatus;
import com.example.unitech.dto.currency.CurrencyResponseDto;
import com.example.unitech.dto.user.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponseDto {
    private Long id;
    private CurrencyResponseDto currency;
    private UserResponseDto user;
    private AccountStatus status;
}
