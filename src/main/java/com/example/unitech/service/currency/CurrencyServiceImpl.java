package com.example.unitech.service.currency;

import static com.example.unitech.persistence.metamodel.currency.Currency_.CURRENCY;
import static com.example.unitech.persistence.metamodel.currency.Currency_.ID;
import static com.example.unitech.util.ErrorUtil.buildNotFoundMessage;

import com.example.unitech.exception.NotFoundException;
import com.example.unitech.persistence.entity.CurrencyEntity;
import com.example.unitech.persistence.repository.CurrencyRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    @Override
    public CurrencyEntity getById(Long id) {
        return currencyRepository
                .findById(id)
                .orElseThrow(
                        () ->
                                new NotFoundException(
                                        buildNotFoundMessage(CURRENCY, ID, String.valueOf(id))));
    }
}
