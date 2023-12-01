package com.example.unitech.client;

import com.example.unitech.dto.currencyrate.CurrencyRateRequestDto;
import com.example.unitech.dto.currencyrate.CurrencyRateResponseDto;

public interface CurrencyRateClient {

    CurrencyRateResponseDto getRate(CurrencyRateRequestDto source);
}
