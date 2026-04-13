package ru.movie.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.movie.entity.Actor;

@RepositoryRestResource(path = "actors")
public interface ActorRepository extends CrudRepository<Actor, Long> {
}
