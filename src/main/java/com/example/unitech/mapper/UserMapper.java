package com.example.unitech.mapper;

import com.example.unitech.dto.UserRequestDto;
import com.example.unitech.dto.UserResponseDto;
import com.example.unitech.persistence.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity toEntity(UserRequestDto source);

    UserResponseDto toResponse(UserEntity source);
}
