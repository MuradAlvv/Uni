package com.example.unitech.service.transfer;

import static com.example.unitech.common.TransferStatus.PENDING;
import static com.example.unitech.common.TransferStatus.SUCCEEDED;

import com.example.unitech.persistence.entity.TransferEntity;
import com.example.unitech.persistence.repository.AccountRepository;
import com.example.unitech.persistence.repository.TransferRepository;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.val;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransferHandlerImpl implements TransferHandler {

    private final TransferRepository transferRepository;
    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public TransferEntity create(TransferEntity transfer) {
        transfer.setStatus(PENDING);
        val amount = transfer.getAmount();
        val fromAccount = transfer.getFromAccount();
        val toAccount = transfer.getToAccount();
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));
        transfer.setStatus(SUCCEEDED);
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        return transferRepository.save(transfer);
    }
}
