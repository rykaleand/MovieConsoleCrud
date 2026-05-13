package ru.movie.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.movie.entity.Movie;
import ru.movie.entity.Report;
import ru.movie.entity.enums.ReportStatus;
import ru.movie.exception.ReportNotFoundException;
import ru.movie.repository.MovieRepository;
import ru.movie.repository.ReportRepository;
import ru.movie.repository.UserRepository;
import ru.movie.service.ReportApi;
import ru.movie.service.util.builder.HtmlReportBuilder;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportService implements ReportApi {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    private final HtmlReportBuilder htmlReportBuilder;

    /**
     * Создаёт отчёт в БД со статусом CREATED и возвращает его id
     * @return id созданного отчёта
     */
    @Override
    @Transactional
    public Long createReport() {
        Report report = Report.builder()
                .status(ReportStatus.CREATED)
                .build();
        return reportRepository.save(report).getId();
    }

    /**
     * Возвращает содержимое отчёта по его id
     * @param id id отчёта
     * @return содержимое отчёта
     * @throws ReportNotFoundException если отчёт не найден
     */
    @Override
    @Transactional(readOnly = true)
    public String getReport(Long id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new ReportNotFoundException(id));

        return switch (report.getStatus()) {
            case CREATED -> "Отчёт ещё формируется...";
            case ERROR -> "Ошибка при формировании отчёта";
            case COMPLETED -> report.getContent();
        };
    }

    /**
     * Асинхронно формирует отчёт в многопоточном режиме
     * @param id id отчёта
     */
    @Override
    public void generateReport(Long id) {
        CompletableFuture.runAsync(() -> {
            Report report = reportRepository.findById(id)
                    .orElseThrow(() -> new ReportNotFoundException(id));

            try {
                long totalStart = System.currentTimeMillis();

                // Задача 1 — подсчёт пользователей
                long userStart = System.currentTimeMillis();
                CompletableFuture<Long> userCountFuture = CompletableFuture.supplyAsync(
                        userRepository::count
                );

                // Задача 2 — получение списка фильмов
                long movieStart = System.currentTimeMillis();
                CompletableFuture<Iterable<Movie>> moviesFuture = CompletableFuture.supplyAsync(
                        () -> (Iterable<Movie>) movieRepository.findAll()
                );

                // Ждём оба результата
                CompletableFuture.allOf(userCountFuture, moviesFuture).join();

                long userTime = System.currentTimeMillis() - userStart;
                long movieTime = System.currentTimeMillis() - movieStart;
                long totalTime = System.currentTimeMillis() - totalStart;

                String content = htmlReportBuilder.build(
                        userCountFuture.get(),
                        userTime,
                        moviesFuture.get(),
                        movieTime,
                        totalTime
                );

                report.setContent(content);
                report.setStatus(ReportStatus.COMPLETED);

            } catch (Exception e) {
                log.error("Ошибка при формировании отчёта id={}", id, e);
                report.setStatus(ReportStatus.ERROR);
            }

            reportRepository.save(report);
        }).exceptionally(e -> {
            log.error("Необработанная ошибка в CompletableFuture id={}", id, e);
            return null;
        });
    }
}
