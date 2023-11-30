package com.example.unitech.service.user;

import com.example.unitech.dto.user.UserCreateDto;
import com.example.unitech.persistence.entity.UserEntity;

public interface UserService {

    UserEntity create(UserEntity user);

    UserEntity create(UserCreateDto userCreateDto);

    UserEntity getById(Long id);

    UserEntity getByPin(String pin);
}
