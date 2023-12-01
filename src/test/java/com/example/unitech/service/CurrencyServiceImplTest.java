package com.example.unitech.service;

import static com.example.unitech.CommonModel.DEFAULT_LONG;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.unitech.exception.NotFoundException;
import com.example.unitech.persistence.entity.CurrencyEntity;
import com.example.unitech.persistence.repository.CurrencyRepository;
import com.example.unitech.service.currency.CurrencyServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceImplTest {

    @Mock private CurrencyRepository currencyRepository;

    @InjectMocks private CurrencyServiceImpl currencyService;

    @Test
    void testGetById() {
        CurrencyEntity currencyEntity = new CurrencyEntity();

        when(currencyRepository.findById(anyLong())).thenReturn(Optional.of(currencyEntity));

        CurrencyEntity result = currencyService.getById(DEFAULT_LONG);

        assertNotNull(result);

        verify(currencyRepository).findById(DEFAULT_LONG);
    }

    @Test
    void testGetByIdWithNotFoundException() {
        when(currencyRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> currencyService.getById(DEFAULT_LONG));

        verify(currencyRepository).findById(DEFAULT_LONG);
    }

    @Test
    void testGetAll() {
        List<CurrencyEntity> currencies = Arrays.asList(new CurrencyEntity(), new CurrencyEntity());
        when(currencyRepository.findAll()).thenReturn(currencies);

        List<CurrencyEntity> result = currencyService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(currencyRepository).findAll();
    }
}
