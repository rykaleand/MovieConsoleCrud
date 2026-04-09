package ru.movie.console.mapper;

import org.springframework.stereotype.Component;
import ru.movie.entity.Movie;
import ru.movie.entity.MovieCreateCommand;
import ru.movie.entity.MovieUpdateCommand;

@Component
public class MovieMapper {

    public Movie toEntity(MovieCreateCommand command) {
        return Movie.builder()
                .title(command.getTitle())
                .genre(command.getGenre())
                .releaseYear(command.getReleaseYear())
                .build();
    }

    public Movie toEntity(MovieUpdateCommand command) {
        return Movie.builder()
                .id(command.getId())
                .title(command.getTitle())
                .genre(command.getGenre())
                .releaseYear(command.getReleaseYear())
                .build();
    }
}
