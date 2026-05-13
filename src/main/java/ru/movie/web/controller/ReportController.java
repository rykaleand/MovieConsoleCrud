package ru.movie.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.movie.service.ReportApi;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportApi reportApi;

    /**
     * Создаёт отчёт и запускает процесс его формирования, не дожидаясь окончания
     * @return id созданного отчёта
     */
    @PostMapping
    public ResponseEntity<Long> createReport() {
        Long id = reportApi.createReport();
        reportApi.generateReport(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(id);
    }

    /**
     * Возвращает содержимое отчёта по его id
     * @param id id отчёта
     * @return содержимое отчёта
     */
    @GetMapping("/{id}")
    public ResponseEntity<String> getReport(@PathVariable Long id) {
        String content = reportApi.getReport(id);
        return ResponseEntity.ok()
                .contentType(org.springframework.http.MediaType.TEXT_HTML)
                .body(content);
    }
}
