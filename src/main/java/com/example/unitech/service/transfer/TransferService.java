package com.example.unitech.service.transfer;

import com.example.unitech.dto.transfer.TransferCreateDto;
import com.example.unitech.persistence.entity.TransferEntity;

public interface TransferService {

    TransferEntity create(TransferCreateDto transferCreateDto);
}
