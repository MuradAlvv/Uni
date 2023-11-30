package com.example.unitech.persistence.metamodel;

import com.example.unitech.persistence.entity.UserEntity;

import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(UserEntity.class)
public class User_ {
    public static final String ID = "id";
    public static final String PIN = "pin";
    public static final String USER = "user";
}
