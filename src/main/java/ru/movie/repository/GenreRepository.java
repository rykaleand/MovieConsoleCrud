package ru.movie.repository;

import org.springframework.data.repository.CrudRepository;
import ru.movie.entity.Genre;

public interface GenreRepository extends CrudRepository<Genre, Long> {
}
