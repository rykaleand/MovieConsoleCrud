package ru.movie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.movie.entity.Director;
import ru.movie.entity.Movie;
import ru.movie.entity.Review;
import ru.movie.entity.User;
import ru.movie.exception.UserNotFoundException;
import ru.movie.repository.DirectorRepository;
import ru.movie.repository.MovieRepository;
import ru.movie.repository.ReviewRepository;
import ru.movie.repository.UserRepository;
import ru.movie.service.ReviewApi;

import java.util.Optional;

@Transactional
@SpringBootTest
class ReviewServiceTest {

    @Autowired
    private ReviewApi reviewApi;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private DirectorRepository directorRepository;

    @Test
    void testCreateReview() {
        Director director = Director.builder()
                .firstName("Steven")
                .lastName("Spielberg")
                .birthYear(1946)
                .build();
        directorRepository.save(director);

        Movie movie = Movie.builder()
                .title("Schindler's List")
                .releaseYear(1993)
                .duration(195)
                .director(director)
                .build();
        movieRepository.save(movie);

        User user = User.builder()
                .username("testuser")
                .email("test@test.com")
                .password("hash123") // переименовали поле
                .build();
        userRepository.save(user);

        reviewApi.createReview(movie.getId(), user.getId(), 10, "Masterpiece");

        Iterable<Review> reviews = reviewRepository.findAll();
        Assertions.assertTrue(reviews.iterator().hasNext());
    }

    @Test
    void testCreateReviewRollbackWhenMovieNotFound() {
        User user = User.builder()
                .username("testuser2")
                .email("test2@test.com")
                .password("hash123")
                .build();
        userRepository.save(user);

        Assertions.assertThrows(RuntimeException.class, () ->
                reviewApi.createReview(999999L, user.getId(), 5, "Test")
        );
    }

    @Test
    void testDeleteUserWithReviews() {
        Director director = Director.builder()
                .firstName("Martin")
                .lastName("Scorsese")
                .birthYear(1942)
                .build();
        directorRepository.save(director);

        Movie movie = Movie.builder()
                .title("Goodfellas")
                .releaseYear(1990)
                .duration(146)
                .director(director)
                .build();
        movieRepository.save(movie);

        User user = User.builder()
                .username("testuser3")
                .email("test3@test.com")
                .password("hash123")
                .build();
        userRepository.save(user);

        reviewApi.createReview(movie.getId(), user.getId(), 9, "Great movie");
        reviewApi.deleteUserWithReviews(user.getId());

        Optional<User> deletedUser = userRepository.findById(user.getId());
        Assertions.assertTrue(deletedUser.isEmpty());
    }

    @Test
    void testDeleteUserWithReviewsRollbackWhenUserNotFound() {
        Assertions.assertThrows(UserNotFoundException.class, () ->
                reviewApi.deleteUserWithReviews(999999L)
        );
    }
}
