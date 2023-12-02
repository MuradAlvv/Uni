package com.example.unitech.service.transfer;

import com.example.unitech.dto.transfer.TransferCreateDto;
import com.example.unitech.persistence.entity.AccountEntity;

import java.math.BigDecimal;

public interface TransferValidator {

    void validateForSameAccount(TransferCreateDto transferCreateDto);

    void validateForSameAccount(Long fromAccountId, Long toAccountId);

    void validateAccountActivity(AccountEntity account);

    void validateTransferAmount(AccountEntity fromAccount, BigDecimal amount);

    void validateCurrency(AccountEntity fromAccount, AccountEntity toAccount);
}
