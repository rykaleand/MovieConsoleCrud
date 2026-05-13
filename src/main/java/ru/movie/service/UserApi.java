package ru.movie.service;

import ru.movie.entity.User;
import ru.movie.exception.UserAlreadyExistsException;

public interface UserApi {

    /**
     * Добавляет нового пользователя в систему.
     *
     * @param user пользователь для регистрации
     * @throws UserAlreadyExistsException если пользователь с таким username уже существует
     */
    void addUser(User user);
}