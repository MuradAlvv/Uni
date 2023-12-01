package com.example.unitech.service.currency;

import static com.example.unitech.common.Errors.buildNotFoundMessage;
import static com.example.unitech.persistence.metamodel.Currency_.CURRENCY;
import static com.example.unitech.persistence.metamodel.Currency_.ID;

import com.example.unitech.exception.NotFoundException;
import com.example.unitech.persistence.entity.CurrencyEntity;
import com.example.unitech.persistence.repository.CurrencyRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<CurrencyEntity> getAll() {
        return currencyRepository.findAll();
    }
}
