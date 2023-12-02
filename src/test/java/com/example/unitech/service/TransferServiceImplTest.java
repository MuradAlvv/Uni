package com.example.unitech.service;

import static com.example.unitech.CommonModel.DEFAULT_LONG;
import static com.example.unitech.CommonModel.buildFailedTransfer;
import static com.example.unitech.CommonModel.buildFromAccount;
import static com.example.unitech.CommonModel.buildToAccount;
import static com.example.unitech.CommonModel.buildTransferCreateDto;
import static com.example.unitech.common.AccountStatus.INACTIVE;
import static com.example.unitech.common.TransferStatus.FAILED;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.example.unitech.common.TransferStatus;
import com.example.unitech.dto.transfer.TransferCreateDto;
import com.example.unitech.exception.InvalidOperationException;
import com.example.unitech.persistence.entity.AccountEntity;
import com.example.unitech.persistence.entity.TransferEntity;
import com.example.unitech.persistence.repository.TransferRepository;
import com.example.unitech.service.account.AccountService;
import com.example.unitech.service.transfer.TransferHandler;
import com.example.unitech.service.transfer.TransferServiceImpl;
import com.example.unitech.service.transfer.TransferValidator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
class TransferServiceImplTest {

    @Mock private AccountService accountService;

    @Mock private TransferHandler transferHandler;

    @Mock private TransferRepository transferRepository;

    @Mock private TransferValidator transferValidator;

    @Mock private ApplicationEventPublisher eventPublisher;

    @InjectMocks private TransferServiceImpl transferService;

    @Test
    void testCreateSuccessfulTransfer() {
        TransferCreateDto transferCreateDto = buildTransferCreateDto();
        AccountEntity fromAccount = buildFromAccount();
        AccountEntity toAccount = buildToAccount();

        when(accountService.getById(fromAccount.getId())).thenReturn(fromAccount);
        when(accountService.getById(toAccount.getId())).thenReturn(toAccount);
        when(transferRepository.save(any(TransferEntity.class)))
                .thenAnswer(
                        invocation -> {
                            TransferEntity transferEntity = invocation.getArgument(0);
                            transferEntity.setId(DEFAULT_LONG);
                            return transferEntity;
                        });

        TransferEntity result = transferService.create(transferCreateDto);

        assertNotNull(result);
        assertEquals(TransferStatus.SUCCEEDED, result.getStatus());
        assertEquals(BigDecimal.TEN, result.getAmount());

        verify(accountService, times(2)).getById(anyLong());
        verify(transferRepository, times(2)).save(any(TransferEntity.class));
        verify(transferHandler).handle(any(TransferEntity.class));
    }

    @Test
    void testCreateFailedTransfer() {
        TransferCreateDto transferCreateDto = buildTransferCreateDto();
        AccountEntity fromAccount = buildFromAccount();
        AccountEntity toAccount = buildToAccount();
        TransferEntity transfer = buildFailedTransfer();

        when(accountService.getById(fromAccount.getId())).thenReturn(fromAccount);
        when(accountService.getById(toAccount.getId())).thenReturn(toAccount);
        when(transferRepository.save(any(TransferEntity.class)))
                .thenAnswer(
                        invocation -> {
                            TransferEntity transferEntity = invocation.getArgument(0);
                            transferEntity.setId(DEFAULT_LONG);
                            return transferEntity;
                        });
        doThrow(new RuntimeException("error in transferHandler"))
                .when(transferHandler)
                .handle(any(TransferEntity.class));
        when(transferRepository.getByIdFetchUser(DEFAULT_LONG)).thenReturn(transfer);
        TransferEntity result = transferService.create(transferCreateDto);

        assertEquals(FAILED, result.getStatus());

        verify(accountService, times(2)).getById(anyLong());
        verify(transferRepository, times(2)).save(any(TransferEntity.class));
        verify(transferHandler).handle(any(TransferEntity.class));
    }

    @Test
    public void testThrowInvalidOperationOnSameAccountTransfer() {
        TransferCreateDto transferCreateDto = buildTransferCreateDto();
        transferCreateDto.setFromAccountId(transferCreateDto.getToAccountId());
        doThrow(InvalidOperationException.class)
                .when(transferValidator)
                .validateForSameAccount(transferCreateDto);
        assertThrows(
                InvalidOperationException.class, () -> transferService.create(transferCreateDto));
        verifyNoInteractions(accountService);
        verifyNoInteractions(transferRepository);
        verifyNoInteractions(transferHandler);
    }

    @Test
    public void testThrowInvalidOperationOnInactiveAccountTransfer() {
        TransferCreateDto transferCreateDto = buildTransferCreateDto();
        AccountEntity fromAccount = buildFromAccount();
        fromAccount.setStatus(INACTIVE);
        when(accountService.getById(transferCreateDto.getFromAccountId())).thenReturn(fromAccount);
        doThrow(InvalidOperationException.class)
                .when(transferValidator)
                .validateAccountActivity(fromAccount);
        assertThrows(
                InvalidOperationException.class, () -> transferService.create(transferCreateDto));
        verify(accountService).getById(fromAccount.getId());
        verifyNoInteractions(transferRepository);
        verifyNoInteractions(transferHandler);
    }

    @Test
    public void testThrowInvalidOperationOnTransferToInactiveAccount() {
        TransferCreateDto transferCreateDto = buildTransferCreateDto();
        AccountEntity fromAccount = buildFromAccount();
        AccountEntity toAccount = buildToAccount();
        toAccount.setStatus(INACTIVE);
        when(accountService.getById(transferCreateDto.getFromAccountId())).thenReturn(fromAccount);
        when(accountService.getById(transferCreateDto.getFromAccountId())).thenReturn(toAccount);
        doThrow(InvalidOperationException.class)
                .when(transferValidator)
                .validateAccountActivity(toAccount);
        assertThrows(
                InvalidOperationException.class, () -> transferService.create(transferCreateDto));
        verify(accountService).getById(anyLong());
        verifyNoInteractions(transferRepository);
        verifyNoInteractions(transferHandler);
    }

    @Test
    public void testThrowInvalidOperationOnInsufficientBalance() {
        TransferCreateDto transferCreateDto = buildTransferCreateDto();
        transferCreateDto.setAmount(BigDecimal.valueOf(100));
        AccountEntity fromAccount = buildFromAccount();
        fromAccount.setBalance(BigDecimal.valueOf(0));
        when(accountService.getById(transferCreateDto.getFromAccountId())).thenReturn(fromAccount);
        doThrow(InvalidOperationException.class)
                .when(transferValidator)
                .validateTransferAmount(fromAccount, transferCreateDto.getAmount());
        assertThrows(
                InvalidOperationException.class, () -> transferService.create(transferCreateDto));
        verify(accountService).getById(transferCreateDto.getFromAccountId());
        verifyNoInteractions(transferRepository);
        verifyNoInteractions(transferHandler);
    }
}
