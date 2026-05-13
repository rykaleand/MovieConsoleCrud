package ru.movie.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.movie.entity.User;

import java.util.Optional;

@RepositoryRestResource(path = "users")
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
}