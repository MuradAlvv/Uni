package com.example.unitech.service.user;

import com.example.unitech.exception.AlreadyExistsException;
import com.example.unitech.persistence.entity.UserEntity;
import com.example.unitech.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.unitech.util.Errors.buildAlreadyExistsMessage;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserEntity create(UserEntity user) {
        if (existsByPin(user.getPin())) {
            throw new AlreadyExistsException(buildAlreadyExistsMessage("user", "pin", user.getPin()));
        }

        return userRepository.save(user);
    }

    private boolean existsByPin(String pin) {
        return userRepository.existsByPin(pin);
    }
}
