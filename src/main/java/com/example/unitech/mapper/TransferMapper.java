package com.example.unitech.mapper;

import com.example.unitech.dto.transfer.TransferResponseDto;
import com.example.unitech.persistence.entity.TransferEntity;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransferMapper {

    TransferResponseDto toResponse(TransferEntity source);
}
