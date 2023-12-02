package com.example.unitech.service.transfer;

import static com.example.unitech.common.AccountStatus.INACTIVE;
import static com.example.unitech.common.Errors.Transfer.InsufficientBalanceError.NOT_ENOUGH_BALANCE_MESSAGE;
import static com.example.unitech.common.Errors.Transfer.TransferToInactiveAccountError.INACTIVE_ACCOUNT_TRANSFER_MESSAGE;
import static com.example.unitech.common.Errors.Transfer.TransferToSameAccountError.TRANSFER_TO_SAME_ACCOUNT_MESSAGE;

import static java.lang.Boolean.FALSE;

import com.example.unitech.annotation.Validator;
import com.example.unitech.dto.transfer.TransferCreateDto;
import com.example.unitech.exception.InvalidOperationException;
import com.example.unitech.persistence.entity.AccountEntity;

import java.math.BigDecimal;

@Validator
public class TransferValidatorImpl implements TransferValidator {

    @Override
    public void validateForSameAccount(TransferCreateDto transferCreateDto) {
        validateForSameAccount(
                transferCreateDto.getFromAccountId(), transferCreateDto.getToAccountId());
    }

    @Override
    public void validateForSameAccount(Long fromAccountId, Long toAccountId) {
        if (fromAccountId.equals(toAccountId)) {
            throw new InvalidOperationException(TRANSFER_TO_SAME_ACCOUNT_MESSAGE.getMessage());
        }
    }

    @Override
    public void validateAccountActivity(AccountEntity account) {
        if (account.getStatus().equals(INACTIVE)) {
            throw new InvalidOperationException(INACTIVE_ACCOUNT_TRANSFER_MESSAGE.getMessage());
        }
    }

    @Override
    public void validateTransferAmount(AccountEntity fromAccount, BigDecimal amount) {
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new InvalidOperationException(NOT_ENOUGH_BALANCE_MESSAGE.getMessage());
        }
    }

    @Override
    public void validateCurrency(AccountEntity fromAccount, AccountEntity toAccount) {
        if (FALSE == fromAccount.getCurrency().getId().equals(toAccount.getCurrency().getId())) {
            throw new InvalidOperationException(
                    "can't transfer money from "
                            + fromAccount.getCurrency().getName()
                            + " to "
                            + toAccount.getCurrency().getName());
        }
    }
}
