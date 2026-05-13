package ru.movie.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.movie.entity.Director;

@RepositoryRestResource(path = "directors")
public interface DirectorRepository extends CrudRepository<Director, Long> {
}
