package com.example.unitech.service.currency;

import com.example.unitech.persistence.entity.CurrencyEntity;

import java.util.List;

public interface CurrencyService {

    CurrencyEntity getById(Long id);

    List<CurrencyEntity> getAll();
}
