package com.example.unitech.mapper;

import com.example.unitech.dto.account.AccountDetailedResponseDto;
import com.example.unitech.dto.account.AccountResponseDto;
import com.example.unitech.persistence.entity.AccountEntity;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountDetailedResponseDto toDetailedResponse(AccountEntity source);

    AccountResponseDto toResponse(AccountEntity source);
}
