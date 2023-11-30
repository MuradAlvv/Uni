package com.example.unitech.persistence.metamodel;

import com.example.unitech.persistence.entity.AccountEntity;

import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(AccountEntity.class)
public class Account_ {

    public static final String ID = "id";
    public static final String ACCOUNT = "account";
}
