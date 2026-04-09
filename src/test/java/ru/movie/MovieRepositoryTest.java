package ru.movie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.movie.entity.Director;
import ru.movie.entity.Movie;
import ru.movie.repository.DirectorRepository;
import ru.movie.repository.MovieRepository;

import java.util.List;

@SpringBootTest
class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private DirectorRepository directorRepository;

    @Test
    void testFindByReleaseYearAndDurationBetween() {
        Director director = Director.builder()
                .firstName("Christopher")
                .lastName("Nolan")
                .birthYear(1970)
                .build();
        directorRepository.save(director);

        Movie movie = Movie.builder()
                .title("Inception")
                .releaseYear(2010)
                .duration(148)
                .director(director)
                .build();
        movieRepository.save(movie);

        List<Movie> result = movieRepository.findByReleaseYearAndDurationBetween(2010, 100, 200);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals("Inception", result.getFirst().getTitle());
    }

    @Test
    void testFindByDirectorLastName() {
        Director director = Director.builder()
                .firstName("Quentin")
                .lastName("Tarantino")
                .birthYear(1963)
                .build();
        directorRepository.save(director);

        Movie movie = Movie.builder()
                .title("Pulp Fiction")
                .releaseYear(1994)
                .duration(154)
                .director(director)
                .build();
        movieRepository.save(movie);

        List<Movie> result = movieRepository.findByDirectorLastName("Tarantino");

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals("Pulp Fiction", result.getFirst().getTitle());
    }
}