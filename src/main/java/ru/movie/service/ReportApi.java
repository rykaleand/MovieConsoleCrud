package ru.movie.service;

public interface ReportApi {

    /**
     * Создаёт отчёт в БД со статусом CREATED и возвращает его id
     * @return id созданного отчёта
     */
    Long createReport();

    /**
     * Возвращает содержимое отчёта по его id
     * @param id id отчёта
     * @return содержимое отчёта
     * @throws RuntimeException если отчёт не найден
     */
    String getReport(Long id);

    /**
     * Асинхронно формирует отчёт
     * @param id id отчёта
     */
    void generateReport(Long id);
}
