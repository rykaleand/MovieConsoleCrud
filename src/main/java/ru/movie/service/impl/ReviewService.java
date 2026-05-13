package ru.movie.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.movie.entity.Movie;
import ru.movie.entity.Review;
import ru.movie.entity.User;
import ru.movie.exception.MovieNotFoundException;
import ru.movie.exception.UserNotFoundException;
import ru.movie.repository.MovieRepository;
import ru.movie.repository.ReviewRepository;
import ru.movie.repository.UserRepository;
import ru.movie.service.ReviewApi;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReviewService implements ReviewApi {

    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    /**
     * Создаёт отзыв на фильм от пользователя
     * @param movieId id фильма
     * @param userId  id пользователя
     * @param rating  оценка
     * @param content текст отзыва
     */
    @Override
    @Transactional
    public void createReview(Long movieId, Long userId, Integer rating, String content) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException(movieId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId)); // был movieId — баг

        Review review = Review.builder()
                .movie(movie)
                .user(user)
                .rating(rating)
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();

        reviewRepository.save(review);
    }

    /**
     * Удаляет пользователя вместе со всеми его отзывами
     * @param userId id пользователя
     */
    @Override
    @Transactional
    public void deleteUserWithReviews(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId)); // был RuntimeException

        reviewRepository.deleteAll(user.getReviews());
        userRepository.delete(user);
    }
}