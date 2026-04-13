package ru.movie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.movie.dao.MovieRepositoryCustom;
import ru.movie.entity.Director;
import ru.movie.entity.Movie;
import ru.movie.repository.DirectorRepository;
import ru.movie.repository.MovieRepository;

import java.util.List;

@Transactional
@SpringBootTest
class MovieDaoTest {

    @Autowired
    private MovieRepositoryCustom movieRepositoryCustom;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private DirectorRepository directorRepository;

    @Test
    void testFindByReleaseYearAndDurationBetween() {
        Director director = Director.builder()
                .firstName("James")
                .lastName("Cameron")
                .birthYear(1954)
                .build();
        directorRepository.save(director);

        Movie movie = Movie.builder()
                .title("Avatar")
                .releaseYear(2009)
                .duration(162)
                .director(director)
                .build();
        movieRepository.save(movie);

        List<Movie> result = movieRepositoryCustom.findByReleaseYearAndDurationBetween(2009, 100, 200);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals("Avatar", result.getFirst().getTitle());
    }

    @Test
    void testFindByDirectorLastName() {
        Director director = Director.builder()
                .firstName("Ridley")
                .lastName("Scott")
                .birthYear(1937)
                .build();
        directorRepository.save(director);

        Movie movie = Movie.builder()
                .title("Gladiator")
                .releaseYear(2000)
                .duration(155)
                .director(director)
                .build();
        movieRepository.save(movie);

        List<Movie> result = movieRepositoryCustom.findByDirectorLastName("Scott");

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals("Gladiator", result.getFirst().getTitle());
    }
}