package com.example.unitech.mapper;

import com.example.unitech.dto.account.AccountCreatedResponseDto;
import com.example.unitech.persistence.entity.AccountEntity;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountCreatedResponseDto toResponse(AccountEntity source);
}
