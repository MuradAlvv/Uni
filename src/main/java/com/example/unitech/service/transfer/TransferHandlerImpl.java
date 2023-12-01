package com.example.unitech.service.transfer;

import com.example.unitech.persistence.entity.TransferEntity;
import com.example.unitech.persistence.repository.AccountRepository;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.val;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransferHandlerImpl implements TransferHandler {

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public void handle(TransferEntity transfer) {
        val amount = transfer.getAmount();
        val fromAccount = transfer.getFromAccount();
        val toAccount = transfer.getToAccount();
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }
}
