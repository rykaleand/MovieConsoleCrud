package ru.movie.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.movie.entity.Director;
import ru.movie.entity.Movie;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MovieRepositoryImpl implements MovieRepositoryCustom {

    private final EntityManager entityManager;

    @Override
    public List<Movie> findByReleaseYearAndDurationBetween(Integer releaseYear, Integer durationMin, Integer durationMax) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);
        Root<Movie> root = cq.from(Movie.class);

        Predicate yearPredicate = cb.equal(root.get("releaseYear"), releaseYear);
        Predicate durationPredicate = cb.between(root.get("duration"), durationMin, durationMax);

        cq.select(root).where(cb.and(yearPredicate, durationPredicate));

        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public List<Movie> findByDirectorLastName(String lastName) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);
        Root<Movie> root = cq.from(Movie.class);

        Join<Movie, Director> directorJoin = root.join("director", JoinType.INNER);
        Predicate lastNamePredicate = cb.equal(directorJoin.get("lastName"), lastName);

        cq.select(root).where(lastNamePredicate);

        return entityManager.createQuery(cq).getResultList();
    }
}