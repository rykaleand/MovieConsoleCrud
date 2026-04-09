package ru.movie.repository;

import org.springframework.data.repository.CrudRepository;
import ru.movie.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
}