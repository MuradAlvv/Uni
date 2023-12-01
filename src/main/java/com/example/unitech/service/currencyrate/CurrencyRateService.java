package com.example.unitech.service.currencyrate;

import com.example.unitech.dto.currencyrate.CurrencyRateRequestDto;
import com.example.unitech.dto.currencyrate.CurrencyRateResponseDto;

public interface CurrencyRateService {

    CurrencyRateResponseDto getRate(CurrencyRateRequestDto rate);
}
