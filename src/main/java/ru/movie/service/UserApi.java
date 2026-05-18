package ru.movie.service;

import ru.movie.entity.User;

public interface UserApi {

    /**
     * Добавляет нового пользователя в систему.
     *
     * @param user пользователь для регистрации
     * @throws RuntimeException если пользователь с таким username уже существует
     */
    void addUser(User user);
}