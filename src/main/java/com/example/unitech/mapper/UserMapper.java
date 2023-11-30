package com.example.unitech.mapper;

import com.example.unitech.dto.user.UserCreateDto;
import com.example.unitech.dto.user.UserResponseDto;
import com.example.unitech.persistence.entity.UserEntity;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity toEntity(UserCreateDto source);

    UserResponseDto toResponse(UserEntity source);
}
