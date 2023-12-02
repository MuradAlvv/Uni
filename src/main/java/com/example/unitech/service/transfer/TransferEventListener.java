package com.example.unitech.service.transfer;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class TransferEventListener {

    @TransactionalEventListener
    public void listenTransfer(TransferCompletedEvent source) {
        log.info("Transfer completed with status = " + source.getTransfer().getStatus());
        // additional logic
    }
}
