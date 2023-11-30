package com.example.unitech.persistence.repository;

import com.example.unitech.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByPin(String pin);
}
