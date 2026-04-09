package ru.movie.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.movie.entity.Movie;

import java.util.List;

@RepositoryRestResource(path = "movies")
public interface MovieRepository extends CrudRepository<Movie, Long> {

    List<Movie> findByReleaseYearAndDurationBetween(Integer releaseYear, Integer durationMin, Integer durationMax);

    @Query("SELECT m FROM Movie m WHERE m.director.lastName = :lastName")
    List<Movie> findByDirectorLastName(@Param("lastName") String lastName);
}
