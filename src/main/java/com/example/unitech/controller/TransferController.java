package com.example.unitech.controller;

import com.example.unitech.dto.transfer.TransferCreateDto;
import com.example.unitech.persistence.entity.TransferEntity;
import com.example.unitech.service.transfer.TransferService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/transfers")
public class TransferController {

    private final TransferService transferService;

    @PostMapping
    public TransferEntity create(@RequestBody @Valid TransferCreateDto source) {
        return transferService.create(source);
    }
}
