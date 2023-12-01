package com.example.unitech.service.transfer;

import com.example.unitech.persistence.entity.TransferEntity;

public interface TransferHandler {

    void handle(TransferEntity transfer);
}
