package ru.movie.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.movie.entity.Review;

@RepositoryRestResource(path = "reviews")
public interface ReviewRepository extends CrudRepository<Review, Long> {
}
