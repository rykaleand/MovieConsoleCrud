package ru.movie.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID> {
    void create(T entity);
    Optional<T> findById(ID id);
    void update(T entity);
    void deleteById(ID id);
    List<T> findAll();
}
