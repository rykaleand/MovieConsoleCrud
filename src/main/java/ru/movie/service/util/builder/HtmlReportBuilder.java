package ru.movie.service.util.builder;

import org.springframework.stereotype.Component;
import ru.movie.entity.Movie;

@Component
public class HtmlReportBuilder {

    /**
     * Формирует HTML отчёт со статистикой приложения
     * @param userCount  количество пользователей
     * @param userTime   время вычисления количества пользователей в мс
     * @param movies     список фильмов
     * @param movieTime  время получения списка фильмов в мс
     * @param totalTime  общее время формирования отчёта в мс
     * @return HTML строка с отчётом
     */
    public String build(
            long userCount,
            long userTime,
            Iterable<Movie> movies,
            long movieTime,
            long totalTime
    ) {
        StringBuilder sb = new StringBuilder();
        sb.append("""
                <html>
                <head><meta charset="UTF-8"><title>Отчёт</title></head>
                <body>
                <h1>Статистика приложения</h1>
                """);

        userSection(sb, userCount, userTime);
        moviesSection(sb, movies, movieTime);
        totalSection(sb, totalTime);

        sb.append("</body></html>");
        return sb.toString();
    }

    /**
     * Добавляет секцию с информацией о пользователях
     * @param sb        строковый буфер
     * @param userCount количество пользователей
     * @param userTime  время вычисления в мс
     */
    private void userSection(StringBuilder sb, long userCount, long userTime) {
        sb.append("<h2>Пользователи</h2>");
        sb.append("<table border='1'>");
        sb.append("<tr><th>Показатель</th><th>Значение</th></tr>");
        sb.append("<tr><td>Количество пользователей</td><td>").append(userCount).append("</td></tr>");
        sb.append("<tr><td>Время вычисления</td><td>").append(userTime).append(" мс</td></tr>");
        sb.append("</table>");
    }

    /**
     * Добавляет секцию со списком фильмов
     * @param sb        строковый буфер
     * @param movies    список фильмов
     * @param movieTime время получения списка в мс
     */
    private void moviesSection(StringBuilder sb, Iterable<Movie> movies, long movieTime) {
        sb.append("<h2>Список фильмов</h2>");
        sb.append("<table border='1'>");
        sb.append("<tr><th>ID</th><th>Название</th><th>Год</th><th>Длительность</th></tr>");
        for (Movie movie : movies) {
            sb.append("<tr>")
                    .append("<td>").append(movie.getId()).append("</td>")
                    .append("<td>").append(movie.getTitle()).append("</td>")
                    .append("<td>").append(movie.getReleaseYear()).append("</td>")
                    .append("<td>").append(movie.getDuration()).append("</td>")
                    .append("</tr>");
        }
        sb.append("<tr><td colspan='4'>Время вычисления: ").append(movieTime).append(" мс</td></tr>");
        sb.append("</table>");
    }

    /**
     * Добавляет итоговую секцию с общим временем формирования отчёта
     * @param sb        строковый буфер
     * @param totalTime общее время в мс
     */
    private void totalSection(StringBuilder sb, long totalTime) {
        sb.append("<h2>Итого</h2>");
        sb.append("<table border='1'>");
        sb.append("<tr><th>Общее время формирования отчёта</th><td>").append(totalTime).append(" мс</td></tr>");
        sb.append("</table>");
    }
}