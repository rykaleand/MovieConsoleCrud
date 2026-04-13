package ru.movie.repository;

import org.springframework.data.repository.CrudRepository;
import ru.movie.entity.Review;

public interface ReviewRepository extends CrudRepository<Review, Long> {
}
