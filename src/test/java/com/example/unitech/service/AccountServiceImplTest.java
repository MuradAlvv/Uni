package com.example.unitech.service;

import static com.example.unitech.CommonModel.DEFAULT_LONG;
import static com.example.unitech.CommonModel.buildAccountCreateDto;
import static com.example.unitech.CommonModel.buildActiveAccount;
import static com.example.unitech.CommonModel.buildCurrency;
import static com.example.unitech.CommonModel.buildUser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.unitech.common.AccountStatus;
import com.example.unitech.dto.account.AccountCreateDto;
import com.example.unitech.dto.account.UserAccountResponseDto;
import com.example.unitech.exception.NotFoundException;
import com.example.unitech.persistence.entity.AccountEntity;
import com.example.unitech.persistence.entity.CurrencyEntity;
import com.example.unitech.persistence.entity.UserEntity;
import com.example.unitech.persistence.repository.AccountRepository;
import com.example.unitech.service.account.AccountServiceImpl;
import com.example.unitech.service.currency.CurrencyService;
import com.example.unitech.service.user.UserService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock private AccountRepository accountRepository;

    @Mock private UserService userService;

    @Mock private CurrencyService currencyService;

    @InjectMocks private AccountServiceImpl accountService;

    @Test
    void testCreate() {
        AccountCreateDto accountCreateDto = buildAccountCreateDto();
        CurrencyEntity currencyEntity = buildCurrency();
        UserEntity userEntity = buildUser();

        when(currencyService.getById(DEFAULT_LONG)).thenReturn(currencyEntity);
        when(userService.getById(DEFAULT_LONG)).thenReturn(userEntity);

        accountService.create(accountCreateDto);

        verify(currencyService).getById(DEFAULT_LONG);
        verify(userService).getById(DEFAULT_LONG);
        verify(accountRepository).save(any(AccountEntity.class));
    }

    @Test
    void testCreateWithAccountEntity() {
        AccountEntity accountEntity = buildActiveAccount();
        when(accountRepository.save(any(AccountEntity.class))).thenReturn(accountEntity);
        AccountEntity result = accountService.create(accountEntity);

        assertEquals(AccountStatus.ACTIVE, result.getStatus());
        assertEquals(BigDecimal.valueOf(0), result.getBalance());

        verify(accountRepository).save(accountEntity);
    }

    @Test
    void testGetById() {
        AccountEntity accountEntity = mock(AccountEntity.class);

        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(accountEntity));

        AccountEntity result = accountService.getById(DEFAULT_LONG);

        assertNotNull(result);

        verify(accountRepository).findById(DEFAULT_LONG);
    }

    @Test
    void testGetByIdWithNotFoundException() {
        when(accountRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> accountService.getById(DEFAULT_LONG));

        verify(accountRepository).findById(DEFAULT_LONG);
    }

    @Test
    void testGetByUserId() {
        UserAccountResponseDto userAccountResponseDto = mock(UserAccountResponseDto.class);
        List<UserAccountResponseDto> accountResponseList = Arrays.asList(userAccountResponseDto);

        when(userService.getById(anyLong())).thenReturn(buildUser());
        when(accountRepository.getByUserId(anyLong())).thenReturn(accountResponseList);

        List<UserAccountResponseDto> result = accountService.getByUserId(DEFAULT_LONG);

        assertNotNull(result);
        assertEquals(1, result.size());

        verify(userService).getById(DEFAULT_LONG);
        verify(accountRepository).getByUserId(DEFAULT_LONG);
    }
}
