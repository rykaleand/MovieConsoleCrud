package ru.movie.repository;

import org.springframework.data.repository.CrudRepository;
import ru.movie.entity.Actor;

public interface ActorRepository extends CrudRepository<Actor, Long> {
}
