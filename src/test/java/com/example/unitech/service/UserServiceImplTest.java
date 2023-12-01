package com.example.unitech.service;

import static com.example.unitech.CommonModel.DEFAULT_LONG;
import static com.example.unitech.CommonModel.DEFAULT_STRING;
import static com.example.unitech.CommonModel.buildUser;
import static com.example.unitech.CommonModel.buildUserCreateDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.unitech.dto.user.UserCreateDto;
import com.example.unitech.exception.AlreadyExistsException;
import com.example.unitech.exception.NotFoundException;
import com.example.unitech.mapper.UserMapper;
import com.example.unitech.persistence.entity.UserEntity;
import com.example.unitech.persistence.repository.UserRepository;
import com.example.unitech.service.user.UserServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock private UserRepository userRepository;

    @Mock private UserMapper userMapper;

    @Mock private PasswordEncoder passwordEncoder;

    @InjectMocks private UserServiceImpl userService;

    @Test
    void testCreate() {
        UserEntity userEntity = buildUser();

        when(userRepository.existsByPin(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn(DEFAULT_STRING);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserEntity result = userService.create(userEntity);

        assertNotNull(result);
        assertEquals(DEFAULT_STRING, result.getPassword());

        verify(userRepository).existsByPin(DEFAULT_STRING);
        verify(passwordEncoder).encode(anyString());
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void testCreateWithAlreadyExistsException() {
        UserEntity userEntity = buildUser();
        when(userRepository.existsByPin(anyString())).thenReturn(true);
        assertThrows(AlreadyExistsException.class, () -> userService.create(userEntity));
        verify(userRepository).existsByPin(DEFAULT_STRING);
    }

    @Test
    void testCreateWithDto() {
        UserCreateDto userCreateDto = buildUserCreateDto();

        UserEntity userEntity = buildUser();

        when(userMapper.toEntity(any(UserCreateDto.class))).thenReturn(userEntity);
        when(userRepository.existsByPin(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn(DEFAULT_STRING);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserEntity result = userService.create(userCreateDto);

        assertNotNull(result);
        assertEquals(DEFAULT_STRING, result.getPassword());

        verify(userMapper).toEntity(userCreateDto);
        verify(userRepository).existsByPin(DEFAULT_STRING);
        verify(passwordEncoder).encode(anyString());
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void testGetById() {
        UserEntity userEntity = buildUser();

        when(userRepository.findById(anyLong())).thenReturn(java.util.Optional.of(userEntity));

        UserEntity result = userService.getById(DEFAULT_LONG);

        assertNotNull(result);

        verify(userRepository).findById(DEFAULT_LONG);
    }

    @Test
    void testGetByIdWithNotFoundException() {
        when(userRepository.findById(anyLong())).thenReturn(java.util.Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getById(DEFAULT_LONG));

        verify(userRepository).findById(DEFAULT_LONG);
    }

    @Test
    void testGetByPin() {
        UserEntity userEntity = new UserEntity();

        when(userRepository.findByPin(anyString())).thenReturn(java.util.Optional.of(userEntity));

        UserEntity result = userService.getByPin(DEFAULT_STRING);

        assertNotNull(result);

        verify(userRepository).findByPin(DEFAULT_STRING);
    }

    @Test
    void testGetByPinWithNotFoundException() {
        when(userRepository.findByPin(anyString())).thenReturn(java.util.Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getByPin(DEFAULT_STRING));

        verify(userRepository).findByPin(DEFAULT_STRING);
    }
}
