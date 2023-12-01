package com.example.unitech.service.currencyrate;

import com.example.unitech.client.CurrencyRateClient;
import com.example.unitech.dto.currencyrate.CurrencyRateRequestDto;
import com.example.unitech.dto.currencyrate.CurrencyRateResponseDto;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyRateServiceImpl implements CurrencyRateService {

    private final CurrencyRateClient currencyRateClient;

    @Override
    @Cacheable("rate")
    public CurrencyRateResponseDto getRate(CurrencyRateRequestDto rate) {
        return currencyRateClient.getRate(rate);
    }
}
