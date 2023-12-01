package com.example.unitech.mapper;

import com.example.unitech.dto.currency.CurrencyResponseDto;
import com.example.unitech.persistence.entity.CurrencyEntity;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {

    CurrencyResponseDto toResponse(CurrencyEntity currency);

    List<CurrencyResponseDto> toResponse(List<CurrencyEntity> currency);
}
