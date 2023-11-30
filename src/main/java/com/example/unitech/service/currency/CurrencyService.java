package com.example.unitech.service.currency;

import com.example.unitech.persistence.entity.CurrencyEntity;

public interface CurrencyService {

    CurrencyEntity getById(Long id);
}
