package ru.movie.console;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.movie.entity.Movie;
import ru.movie.service.MovieApi;

@Component
@RequiredArgsConstructor
public class CommandProcessor {

    private final MovieApi movieApi;

    public void processCommand(String input) {
        String[] cmd = input.split(" ");
        switch (cmd[0]) {
            case "create" -> {
                movieApi.createMovie(
                        Long.valueOf(cmd[1]),
                        cmd[2],
                        cmd[3],
                        Integer.valueOf(cmd[4])
                );
                System.out.println("Movie successfully created");
            }
            case "find" -> {
                Movie movie = movieApi.findById(Long.valueOf(cmd[1]));
                if (movie != null) {
                    System.out.println(movie);
                }
            }
            case "update" -> {
                movieApi.updateMovie(
                        Long.valueOf(cmd[1]),
                        cmd[2],
                        cmd[3],
                        Integer.valueOf(cmd[4])
                );
                System.out.println("Movie successfully updated");
            }
            case "delete" -> {
                movieApi.deleteById(Long.valueOf(cmd[1]));
                System.out.println("Movie successfully deleted");
            }
            case "list" -> {
                movieApi.findAll().forEach(System.out::println);
            }
            default -> System.out.println("Unknown command");
        }
    }
}
