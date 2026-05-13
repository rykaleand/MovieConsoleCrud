package ru.movie.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.movie.entity.Genre;

@RepositoryRestResource(path = "genres")
public interface GenreRepository extends CrudRepository<Genre, Long> {
}
