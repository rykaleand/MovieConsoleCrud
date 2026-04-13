package ru.movie.repository;

import org.springframework.data.repository.CrudRepository;
import ru.movie.entity.Director;

public interface DirectorRepository extends CrudRepository<Director, Long> {
}
