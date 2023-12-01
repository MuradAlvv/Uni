package com.example.unitech.service.currencyrate;

import com.example.unitech.client.CurrencyRateClient;
import com.example.unitech.dto.currencyrate.CurrencyRateRequestDto;

import lombok.RequiredArgsConstructor;
import lombok.val;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyRateServiceImpl {

    private final CurrencyRateClient currencyRateClient;

    @Cacheable("rate")
    public Double getRate(String rate) {
        val request = new CurrencyRateRequestDto(rate);
        return currencyRateClient.getRate(request);
    }
}
