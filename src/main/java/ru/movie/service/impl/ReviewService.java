package ru.movie.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import ru.movie.entity.Movie;
import ru.movie.entity.Review;
import ru.movie.entity.User;
import ru.movie.exception.MovieNotFoundException;
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

    @Override
    @Transactional
    public void createReview(Long movieId, Long userId, Integer rating, String content) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException(movieId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new MovieNotFoundException(movieId));

        Review review = Review.builder()
                .movie(movie)
                .user(user)
                .rating(rating)
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();

        reviewRepository.save(review);
    }

    @Override
    @Transactional
    public void deleteUserWithReviews(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        reviewRepository.deleteAll(user.getReviews());
        userRepository.delete(user);
    }
}