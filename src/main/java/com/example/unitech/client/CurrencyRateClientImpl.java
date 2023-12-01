package com.example.unitech.client;

import com.example.unitech.dto.currencyrate.CurrencyRateRequestDto;

import org.springframework.stereotype.Component;

/** ThirdParty client */
@Component
public class CurrencyRateClientImpl implements CurrencyRateClient {

    /** Assuming that every call of this method has cost */
    @Override
    public Double getRate(CurrencyRateRequestDto source) {
        return 1.7;
    }
}
