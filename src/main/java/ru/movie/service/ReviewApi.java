package ru.movie.service;

public interface ReviewApi {

    void createReview(Long movieId, Long userId, Integer rating, String content);

    void deleteUserWithReviews(Long userId);
}