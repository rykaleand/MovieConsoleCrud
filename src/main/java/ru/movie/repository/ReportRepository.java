package ru.movie.repository;

import org.springframework.data.repository.CrudRepository;
import ru.movie.entity.Report;

public interface ReportRepository extends CrudRepository<Report, Long> {
}
