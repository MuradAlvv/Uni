package com.example.unitech.service.account;

import static com.example.unitech.common.AccountStatus.ACTIVE;
import static com.example.unitech.common.Errors.buildNotFoundMessage;
import static com.example.unitech.persistence.metamodel.Account_.ACCOUNT;
import static com.example.unitech.persistence.metamodel.Account_.ID;

import com.example.unitech.dto.account.AccountCreateDto;
import com.example.unitech.dto.account.UserAccountResponseDto;
import com.example.unitech.exception.NotFoundException;
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

        return create(account);
    }

    @Override
    public AccountEntity create(AccountEntity account) {
        account.setStatus(ACTIVE);
        account.setBalance(BigDecimal.valueOf(0));

        return accountRepository.save(account);
    }

    @Override
    public AccountEntity getById(Long id) {
        return accountRepository
                .findById(id)
                .orElseThrow(
                        () ->
                                new NotFoundException(
                                        buildNotFoundMessage(ACCOUNT, ID, String.valueOf(id))));
    }

    @Override
    public List<UserAccountResponseDto> getByUserId(Long id) {
        userService.getById(id);

        return accountRepository.getByUserId(id);
    }
}
