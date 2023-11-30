package com.example.unitech.service.account;

import static com.example.unitech.common.AccountStatus.ACTIVE;

import com.example.unitech.dto.account.AccountCreateDto;
import com.example.unitech.dto.account.UserAccountResponseDto;
import com.example.unitech.persistence.entity.AccountEntity;
import com.example.unitech.persistence.repository.AccountRepository;
import com.example.unitech.service.currency.CurrencyService;
import com.example.unitech.service.user.UserService;

import lombok.RequiredArgsConstructor;
import lombok.val;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserService userService;
    private final CurrencyService currencyService;

    @Override
    public AccountEntity create(AccountCreateDto accountCreateDto) {
        val account = new AccountEntity();
        account.setCurrency(currencyService.getById(accountCreateDto.getCurrencyId()));
        account.setUser(userService.getById(accountCreateDto.getUserId()));
        account.setStatus(ACTIVE);
        account.setBalance(BigDecimal.valueOf(0));

        return create(account);
    }

    @Override
    public AccountEntity create(AccountEntity account) {
        return accountRepository.save(account);
    }

    @Override
    public List<UserAccountResponseDto> getByUserId(Long id) {
        userService.getById(id);
        return accountRepository.getByUserId(id);
    }
}
