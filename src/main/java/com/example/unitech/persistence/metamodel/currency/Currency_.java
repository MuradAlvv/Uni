package com.example.unitech.persistence.metamodel.currency;

import com.example.unitech.persistence.entity.CurrencyEntity;

import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(CurrencyEntity.class)
public class Currency_ {

    public static final String ID = "id";
    public static final String CURRENCY = "currency";
}
