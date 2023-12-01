package com.example.unitech.service.transfer;

import static com.example.unitech.common.AccountStatus.INACTIVE;
import static com.example.unitech.common.Errors.Transfer.InsufficientBalanceError.NOT_ENOUGH_BALANCE_MESSAGE;
import static com.example.unitech.common.Errors.Transfer.TransferToInactiveAccountError.INACTIVE_ACCOUNT_TRANSFER_MESSAGE;
import static com.example.unitech.common.Errors.Transfer.TransferToSameAccountError.TRANSFER_TO_SAME_ACCOUNT_MESSAGE;
import static com.example.unitech.common.TransferStatus.FAILED;
import static com.example.unitech.common.TransferStatus.PENDING;
import static com.example.unitech.common.TransferStatus.SUCCEEDED;

import static java.lang.Boolean.FALSE;

import com.example.unitech.dto.transfer.TransferCreateDto;
import com.example.unitech.exception.InvalidOperationException;
import com.example.unitech.persistence.entity.AccountEntity;
import com.example.unitech.persistence.entity.TransferEntity;
import com.example.unitech.persistence.repository.TransferRepository;
import com.example.unitech.service.account.AccountService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final AccountService accountService;
    private final TransferHandler transferHandler;
    private final TransferRepository transferRepository;

    @Override
    public TransferEntity create(TransferCreateDto transferCreateDto) {
        validateForSameAccount(transferCreateDto);
        val fromAccount = accountService.getById(transferCreateDto.getFromAccountId());
        val toAccount = accountService.getById(transferCreateDto.getToAccountId());
        TransferEntity transfer = new TransferEntity();
        transfer.setAmount(transferCreateDto.getAmount());
        transfer.setFromAccount(fromAccount);
        validateAccountActivity(fromAccount);
        validateTransferAmount(fromAccount, transferCreateDto.getAmount());
        transfer.setToAccount(toAccount);
        validateAccountActivity(toAccount);
        validateCurrency(fromAccount, toAccount);

        beginTransfer(transfer);
        log.info("Transfer started, status = " + transfer.getStatus());
        try {
            transferHandler.handle(transfer);
        } catch (Exception e) {
            log.error("Error in transfer: ", e);
            transfer.setStatus(FAILED);
        }
        transfer.setStatus(SUCCEEDED);
        transferRepository.save(transfer);
        log.info("Transfer status = " + transfer.getStatus());

        return transfer;
    }

    private void validateCurrency(AccountEntity fromAccount, AccountEntity toAccount) {
        if (FALSE == fromAccount.getCurrency().getId().equals(toAccount.getCurrency().getId())) {
            throw new InvalidOperationException(
                    "can't transfer money from "
                            + fromAccount.getCurrency().getName()
                            + " to "
                            + toAccount.getCurrency().getName());
        }
    }

    private void beginTransfer(TransferEntity transfer) {
        transfer.setStatus(PENDING);
        transferRepository.save(transfer);
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
            throw new InvalidOperationException(TRANSFER_TO_SAME_ACCOUNT_MESSAGE.getMessage());
        }
    }

    private void validateAccountActivity(AccountEntity account) {
        if (account.getStatus().equals(INACTIVE)) {
            throw new InvalidOperationException(INACTIVE_ACCOUNT_TRANSFER_MESSAGE.getMessage());
        }
    }

    private void validateTransferAmount(AccountEntity fromAccount, BigDecimal amount) {
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new InvalidOperationException(NOT_ENOUGH_BALANCE_MESSAGE.getMessage());
        }
    }
}
