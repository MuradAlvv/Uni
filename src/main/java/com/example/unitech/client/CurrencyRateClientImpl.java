package com.example.unitech.client;

import com.example.unitech.dto.currencyrate.CurrencyRateRequestDto;
import com.example.unitech.dto.currencyrate.CurrencyRateResponseDto;

import org.springframework.stereotype.Component;

/** ThirdParty client */
@Component
public class CurrencyRateClientImpl implements CurrencyRateClient {

    /** Assuming that every call of this method has cost */
    @Override
    public CurrencyRateResponseDto getRate(CurrencyRateRequestDto source) {
        return new CurrencyRateResponseDto(source.getPair(), 1.7);
    }
}
