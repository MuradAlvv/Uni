package com.example.unitech.service.transfer;

import com.example.unitech.persistence.entity.TransferEntity;

import lombok.Getter;

import org.springframework.context.ApplicationEvent;

@Getter
public class TransferCompletedEvent extends ApplicationEvent {

    private final TransferEntity transfer;

    public TransferCompletedEvent(Object source, TransferEntity transfer) {
        super(source);
        this.transfer = transfer;
    }
}
