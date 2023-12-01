package com.example.unitech;

import static com.example.unitech.common.TransferStatus.FAILED;
import static lombok.AccessLevel.PRIVATE;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.unitech.common.AccountStatus;
import com.example.unitech.dto.account.AccountCreateDto;
import com.example.unitech.dto.transfer.TransferCreateDto;
import com.example.unitech.dto.user.UserCreateDto;
import com.example.unitech.persistence.entity.AccountEntity;
import com.example.unitech.persistence.entity.CurrencyEntity;
import com.example.unitech.persistence.entity.TransferEntity;
import com.example.unitech.persistence.entity.UserEntity;

import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = PRIVATE)
public final class CommonModel {
    public static final String DEFAULT_STRING = "default";
    public static final Long DEFAULT_LONG = 1L;

    public static UserEntity buildUser() {
        return new UserEntity(DEFAULT_LONG, DEFAULT_STRING, DEFAULT_STRING);
    }

    public static UserCreateDto buildUserCreateDto() {
        return new UserCreateDto(DEFAULT_STRING, DEFAULT_STRING);
    }

    public static AccountCreateDto buildAccountCreateDto() {
        return new AccountCreateDto(DEFAULT_LONG, DEFAULT_LONG);
    }

    public static AccountEntity buildActiveAccount() {
        AccountEntity accountEntity = mock(AccountEntity.class);
        when(accountEntity.getStatus()).thenReturn(AccountStatus.ACTIVE);
        when(accountEntity.getBalance()).thenReturn(BigDecimal.valueOf(0));

        return accountEntity;
    }

    public static CurrencyEntity buildCurrency() {
        CurrencyEntity currency = new CurrencyEntity();
        currency.setId(DEFAULT_LONG);
        return currency;
    }

    public static TransferCreateDto buildTransferCreateDto() {
        TransferCreateDto transferCreateDto = new TransferCreateDto();
        transferCreateDto.setFromAccountId(DEFAULT_LONG);
        transferCreateDto.setToAccountId(DEFAULT_LONG + 1);
        transferCreateDto.setAmount(BigDecimal.TEN);

        return transferCreateDto;
    }

    public static AccountEntity buildFromAccount() {
        AccountEntity fromAccount = new AccountEntity();
        fromAccount.setId(DEFAULT_LONG);
        fromAccount.setBalance(BigDecimal.valueOf(100));
        fromAccount.setStatus(AccountStatus.ACTIVE);
        fromAccount.setCurrency(buildCurrency());

        return fromAccount;
    }

    public static AccountEntity buildToAccount() {
        AccountEntity toAccount = new AccountEntity();
        toAccount.setId(DEFAULT_LONG + 1);
        toAccount.setBalance(BigDecimal.valueOf(50));
        toAccount.setStatus(AccountStatus.ACTIVE);
        toAccount.setCurrency(buildCurrency());

        return toAccount;
    }

    public static TransferEntity buildFailedTransfer() {
        TransferEntity transfer = new TransferEntity();
        transfer.setStatus(FAILED);

        return transfer;
    }
}
