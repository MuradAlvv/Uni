package com.example.unitech.service.account;

import com.example.unitech.dto.account.AccountCreateDto;
import com.example.unitech.dto.account.UserAccountResponseDto;
import com.example.unitech.persistence.entity.AccountEntity;

import java.util.List;

public interface AccountService {

    AccountEntity create(AccountCreateDto account);

    AccountEntity create(AccountEntity account);

    List<UserAccountResponseDto> getByUserId(Long id);
}
