package ru.movie.mockito;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.movie.dao.MovieRepositoryCustom;
import ru.movie.entity.Director;
import ru.movie.entity.Movie;
import ru.movie.exception.MovieNotFoundException;
import ru.movie.repository.MovieRepository;
import ru.movie.service.impl.MovieService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private MovieRepositoryCustom movieRepositoryCustom;

    @InjectMocks
    private MovieService movieService;

    // ===== getById =====

    @Test
    void getById_shouldReturnMovie_whenExists() {
        Movie movie = Movie.builder()
                .id(1L)
                .title("Inception")
                .releaseYear(2010)
                .duration(148)
                .build();

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        Movie result = movieService.getById(1L);

        assertNotNull(result);
        assertEquals("Inception", result.getTitle());
        verify(movieRepository, times(1)).findById(1L);
    }

    @Test
    void getById_shouldThrowMovieNotFoundException_whenNotExists() {
        when(movieRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(MovieNotFoundException.class, () -> movieService.getById(999L));
        verify(movieRepository, times(1)).findById(999L);
    }

    // ===== save =====

    @Test
    void save_shouldReturnSavedMovie() {
        Movie movie = Movie.builder()
                .title("Interstellar")
                .releaseYear(2014)
                .duration(169)
                .build();

        Movie saved = Movie.builder()
                .id(1L)
                .title("Interstellar")
                .releaseYear(2014)
                .duration(169)
                .build();

        when(movieRepository.save(movie)).thenReturn(saved);

        Movie result = movieService.save(movie);

        assertNotNull(result.getId());
        assertEquals("Interstellar", result.getTitle());
        verify(movieRepository, times(1)).save(movie);
    }

    // ===== findByReleaseYearAndDuration =====

    @Test
    void findByReleaseYearAndDuration_shouldReturnMovies() {
        List<Movie> movies = List.of(
                Movie.builder().title("Movie1").releaseYear(2010).duration(120).build(),
                Movie.builder().title("Movie2").releaseYear(2010).duration(130).build()
        );

        when(movieRepositoryCustom.findByReleaseYearAndDurationBetween(2010, 100, 150))
                .thenReturn(movies);

        List<Movie> result = movieService.findByReleaseYearAndDuration(2010, 100, 150);

        assertEquals(2, result.size());
        verify(movieRepositoryCustom, times(1))
                .findByReleaseYearAndDurationBetween(2010, 100, 150);
    }

    @Test
    void findByReleaseYearAndDuration_shouldReturnEmptyList_whenNoMoviesFound() {
        when(movieRepositoryCustom.findByReleaseYearAndDurationBetween(1900, 0, 1))
                .thenReturn(List.of());

        List<Movie> result = movieService.findByReleaseYearAndDuration(1900, 0, 1);

        assertTrue(result.isEmpty());
        verify(movieRepositoryCustom, times(1))
                .findByReleaseYearAndDurationBetween(1900, 0, 1);
    }

    // ===== findByDirectorLastName =====

    @Test
    void findByDirectorLastName_shouldReturnMovies() {
        Director director = Director.builder()
                .firstName("Christopher")
                .lastName("Nolan")
                .build();

        List<Movie> movies = List.of(
                Movie.builder().title("Inception").director(director).build(),
                Movie.builder().title("Interstellar").director(director).build()
        );

        when(movieRepositoryCustom.findByDirectorLastName("Nolan")).thenReturn(movies);

        List<Movie> result = movieService.findByDirectorLastName("Nolan");

        assertEquals(2, result.size());
        verify(movieRepositoryCustom, times(1)).findByDirectorLastName("Nolan");
    }

    @Test
    void findByDirectorLastName_shouldReturnEmptyList_whenDirectorNotFound() {
        when(movieRepositoryCustom.findByDirectorLastName("Unknown")).thenReturn(List.of());

        List<Movie> result = movieService.findByDirectorLastName("Unknown");

        assertTrue(result.isEmpty());
        verify(movieRepositoryCustom, times(1)).findByDirectorLastName("Unknown");
    }
}
