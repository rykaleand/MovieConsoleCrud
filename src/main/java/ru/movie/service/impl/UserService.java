package ru.movie.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.movie.entity.User;
import ru.movie.entity.enums.Role;
import ru.movie.exception.UserAlreadyExistsException;
import ru.movie.repository.UserRepository;
import ru.movie.service.UserApi;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserApi {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Добавляет нового пользователя в систему.
     *
     * @param user пользователь для регистрации
     * @throws UserAlreadyExistsException если пользователь с таким username уже существует
     */
    public void addUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException(user.getUsername());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
    }
}