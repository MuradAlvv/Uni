package com.example.unitech.service.user;

import static com.example.unitech.persistence.metamodel.user.User_.ID;
import static com.example.unitech.persistence.metamodel.user.User_.PIN;
import static com.example.unitech.persistence.metamodel.user.User_.USER;
import static com.example.unitech.util.ErrorUtil.buildAlreadyExistsMessage;
import static com.example.unitech.util.ErrorUtil.buildNotFoundMessage;

import com.example.unitech.dto.user.UserCreateDto;
import com.example.unitech.exception.AlreadyExistsException;
import com.example.unitech.exception.NotFoundException;
import com.example.unitech.mapper.UserMapper;
import com.example.unitech.persistence.entity.UserEntity;
import com.example.unitech.persistence.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserEntity create(UserEntity user) {
        if (existsByPin(user.getPin())) {
            throw new AlreadyExistsException(buildAlreadyExistsMessage(USER, PIN, user.getPin()));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public UserEntity create(UserCreateDto userCreateDto) {
        return create(userMapper.toEntity(userCreateDto));
    }

    @Override
    public UserEntity getById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(
                        () ->
                                new NotFoundException(
                                        buildNotFoundMessage(USER, ID, String.valueOf(id))));
    }

    @Override
    public UserEntity getByPin(String pin) {
        return userRepository
                .findByPin(pin)
                .orElseThrow(() -> new NotFoundException(buildNotFoundMessage(USER, PIN, pin)));
    }

    private boolean existsByPin(String pin) {
        return userRepository.existsByPin(pin);
    }
}
