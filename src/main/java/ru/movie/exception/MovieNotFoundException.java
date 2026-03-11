package ru.movie.exception;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(String message) {
        super(message);
    }

    public MovieNotFoundException(Long id) {
        super("Movie with %d not found".formatted(id));
    }
}
