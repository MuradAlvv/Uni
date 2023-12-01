package com.example.unitech.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.unitech.persistence.entity.AccountEntity;
import com.example.unitech.persistence.entity.TransferEntity;
import com.example.unitech.persistence.repository.AccountRepository;
import com.example.unitech.service.transfer.TransferHandlerImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
class TransferHandlerImplTest {

    @Mock private AccountRepository accountRepository;

    @InjectMocks private TransferHandlerImpl transferHandler;

    @Test
    @Transactional
    void testHandle() {
        TransferEntity transfer = new TransferEntity();
        transfer.setAmount(BigDecimal.TEN);

        AccountEntity fromAccount = new AccountEntity();
        fromAccount.setBalance(BigDecimal.valueOf(100));
        transfer.setFromAccount(fromAccount);

        AccountEntity toAccount = new AccountEntity();
        toAccount.setBalance(BigDecimal.valueOf(50));
        transfer.setToAccount(toAccount);

        when(accountRepository.save(fromAccount)).thenReturn(fromAccount);
        when(accountRepository.save(toAccount)).thenReturn(toAccount);

        transferHandler.handle(transfer);

        assertEquals(BigDecimal.valueOf(90), fromAccount.getBalance());
        assertEquals(BigDecimal.valueOf(60), toAccount.getBalance());

        verify(accountRepository).save(fromAccount);
        verify(accountRepository).save(toAccount);
    }
}
