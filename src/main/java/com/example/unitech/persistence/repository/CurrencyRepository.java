package com.example.unitech.persistence.repository;

import com.example.unitech.persistence.entity.CurrencyEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<CurrencyEntity, Long> {

    boolean existsById(Long id);
}
