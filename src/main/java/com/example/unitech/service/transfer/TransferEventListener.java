package com.example.unitech.service.transfer;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TransferEventListener {

    @EventListener
    public void listenTransfer(TransferCompletedEvent source) {
        log.info("Transfer completed with status = " + source.getTransfer().getStatus());
        // additional logic
    }
}
