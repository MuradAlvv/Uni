package com.example.unitech.client;

import com.example.unitech.dto.currencyrate.CurrencyRateRequestDto;

public interface CurrencyRateClient {

    Double getRate(CurrencyRateRequestDto source);
}
