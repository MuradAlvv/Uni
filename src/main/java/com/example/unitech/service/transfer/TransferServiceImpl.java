package com.example.unitech.service.transfer;

import static com.example.unitech.common.AccountStatus.INACTIVE;

import com.example.unitech.common.Errors;
import com.example.unitech.dto.transfer.TransferCreateDto;
import com.example.unitech.exception.InvalidOperationException;
import com.example.unitech.persistence.entity.AccountEntity;
import com.example.unitech.persistence.entity.TransferEntity;
import com.example.unitech.service.account.AccountService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final AccountService accountService;
    private final TransferHandler transferHandler;

    @Override
    public TransferEntity create(TransferCreateDto transferCreateDto) {
        validateForSameAccount(transferCreateDto);
        TransferEntity transfer = new TransferEntity();
        transfer.setAmount(transferCreateDto.getAmount());
        transfer.setFromAccount(accountService.getById(transferCreateDto.getFromAccountId()));
        validateAccountActivity(transfer.getFromAccount());
        validateTransferAmount(transfer.getFromAccount(), transferCreateDto.getAmount());
        transfer.setToAccount(accountService.getById(transferCreateDto.getToAccountId()));
        validateAccountActivity(transfer.getToAccount());

        return transferHandler.create(transfer);
    }

    @Override
    public Boolean isUserAccount(Long userId, Long accountId) {
        return accountService.getById(accountId).getUser().getId().equals(userId);
    }

    private void validateForSameAccount(TransferCreateDto transferCreateDto) {
        validateForSameAccount(
                transferCreateDto.getFromAccountId(), transferCreateDto.getToAccountId());
    }

    private void validateForSameAccount(Long fromAccountId, Long toAccountId) {
        if (fromAccountId.equals(toAccountId)) {
            throw new InvalidOperationException(
                    Errors.Transfer.TransferToSameAccountError.TRANSFER_TO_SAME_ACCOUNT_MESSAGE
                            .getValue());
        }
    }

    private void validateAccountActivity(AccountEntity account) {
        if (account.getStatus().equals(INACTIVE)) {
            throw new InvalidOperationException(
                    Errors.Transfer.TransferToInactiveAccountError.INACTIVE_ACCOUNT_TRANSFER_MESSAGE
                            .getValue());
        }
    }

    private void validateTransferAmount(AccountEntity fromAccount, BigDecimal amount) {
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new InvalidOperationException(
                    Errors.Transfer.InsufficientBalanceError.NOT_ENOUGH_BALANCE_MESSAGE.getValue());
        }
    }
}
