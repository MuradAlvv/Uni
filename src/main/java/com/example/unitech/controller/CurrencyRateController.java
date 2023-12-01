package com.example.unitech.controller;

import com.example.unitech.dto.currencyrate.CurrencyRateRequestDto;
import com.example.unitech.dto.currencyrate.CurrencyRateResponseDto;
import com.example.unitech.service.currencyrate.CurrencyRateService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/currency-rates")
@Tag(name = "CurrencyRateController")
public class CurrencyRateController {

    private final CurrencyRateService currencyRateService;

    @Operation(description = "Get currency rate")
    @GetMapping
    public CurrencyRateResponseDto getRate(@RequestParam final String pair) {
        return currencyRateService.getRate(new CurrencyRateRequestDto(pair));
    }
}
