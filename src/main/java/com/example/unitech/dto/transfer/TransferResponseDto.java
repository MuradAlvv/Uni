package com.example.unitech.dto.transfer;

import com.example.unitech.common.TransferStatus;
import com.example.unitech.dto.account.AccountDetailedResponseDto;
import com.example.unitech.dto.account.AccountResponseDto;

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
public class TransferResponseDto {

    private Long id;
    private AccountDetailedResponseDto fromAccount;
    private AccountResponseDto toAccount;
    private BigDecimal amount;
    private TransferStatus status;
    private LocalDateTime createdAt;
}
