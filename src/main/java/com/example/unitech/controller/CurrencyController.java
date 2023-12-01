package com.example.unitech.controller;

import com.example.unitech.dto.currency.CurrencyResponseDto;
import com.example.unitech.mapper.CurrencyMapper;
import com.example.unitech.service.currency.CurrencyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/currencies")
@Tag(name = "CurrencyController")
public class CurrencyController {

    private final CurrencyService currencyService;
    private final CurrencyMapper currencyMapper;

    @Operation(description = "Get all currencies")
    @GetMapping
    public List<CurrencyResponseDto> getAll() {
        return currencyMapper.toResponse(currencyService.getAll());
    }
}
