package com.example.unitech.service.transfer;

import static com.example.unitech.common.TransferStatus.FAILED;
import static com.example.unitech.common.TransferStatus.PENDING;
import static com.example.unitech.common.TransferStatus.SUCCEEDED;

import com.example.unitech.dto.transfer.TransferCreateDto;
import com.example.unitech.persistence.entity.TransferEntity;
import com.example.unitech.persistence.repository.TransferRepository;
import com.example.unitech.service.account.AccountService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final AccountService accountService;
    private final TransferHandler transferHandler;
    private final TransferRepository transferRepository;
    private final TransferValidator transferValidator;

    @Override
    public TransferEntity create(TransferCreateDto transferCreateDto) {
        transferValidator.validateForSameAccount(transferCreateDto);
        val fromAccount = accountService.getById(transferCreateDto.getFromAccountId());
        transferValidator.validateAccountActivity(fromAccount);
        transferValidator.validateTransferAmount(fromAccount, transferCreateDto.getAmount());
        val toAccount = accountService.getById(transferCreateDto.getToAccountId());
        transferValidator.validateAccountActivity(toAccount);
        transferValidator.validateCurrency(fromAccount, toAccount);

        val transfer = new TransferEntity();
        transfer.setAmount(transferCreateDto.getAmount());
        transfer.setFromAccount(fromAccount);
        transfer.setToAccount(toAccount);
        beginTransfer(transfer);
        log.info("Transfer started, status = " + transfer.getStatus());
        try {
            transferHandler.handle(transfer);
        } catch (Exception e) {
            log.error("Error in transfer: ", e);
            transfer.setStatus(FAILED);
            transferRepository.save(transfer);

            return transferRepository.getByIdFetchUser(transfer.getId());
        }
        transfer.setStatus(SUCCEEDED);
        transferRepository.save(transfer);
        log.info("Transfer status = " + transfer.getStatus());

        return transfer;
    }

    private void beginTransfer(TransferEntity transfer) {
        transfer.setStatus(PENDING);
        transferRepository.save(transfer);
    }

    @Override
    public Boolean isUserAccount(Long userId, Long accountId) {
        return accountService.getById(accountId).getUser().getId().equals(userId);
    }
}
