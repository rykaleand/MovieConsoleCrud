package ru.movie.console;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.movie.entity.Movie;
import ru.movie.entity.MovieCreateCommand;
import ru.movie.entity.MovieUpdateCommand;
import ru.movie.exception.MovieNotFoundException;
import ru.movie.service.MovieApi;

@Component
@RequiredArgsConstructor
public class CommandProcessor {

    private final MovieApi movieApi;

    public void processCommand(String input) {
        String[] cmd = input.trim().split("\\s+");
        switch (cmd[0]) {
            case "create" -> {
                if (cmd.length < 4) {
                    System.out.println("Usage: create <title> <genre> <year>");
                    return;
                }
                try {
                    movieApi.createMovie(new MovieCreateCommand(cmd[1], cmd[2], Integer.parseInt(cmd[3])));
                    System.out.println("Movie successfully created");
                } catch (NumberFormatException e) {
                    System.out.println("Invalid year: must be a number");
                }
            }
            case "find" -> {
                if (cmd.length < 2) {
                    System.out.println("Usage: find <id>");
                    return;
                }
                try {
                    System.out.println(movieApi.findById(Long.parseLong(cmd[1])));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid id: must be a number");
                } catch (MovieNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            }
            case "update" -> {
                if (cmd.length < 5) {
                    System.out.println("Usage: update <id> <title> <genre> <year>");
                    return;
                }
                try {
                    movieApi.updateMovie(new MovieUpdateCommand(Long.parseLong(cmd[1]), cmd[2], cmd[3], Integer.parseInt(cmd[4])));
                    System.out.println("Movie successfully updated");
                } catch (NumberFormatException e) {
                    System.out.println("Invalid id or year: must be a number");
                }
            }
            case "delete" -> {
                if (cmd.length < 2) {
                    System.out.println("Usage: delete <id>");
                    return;
                }
                try {
                    movieApi.deleteById(Long.parseLong(cmd[1]));
                    System.out.println("Movie successfully deleted");
                } catch (NumberFormatException e) {
                    System.out.println("Invalid id: must be a number");
                }
            }
            case "list" -> movieApi.findAll().forEach(System.out::println);
            default -> System.out.println("Unknown command");
        }
    }
}
