package ru.movie.web.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.movie.exception.MovieNotFoundException;
import ru.movie.web.advice.model.ErrorResponse;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MovieNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ErrorResponse handleMovieNotFoundException(MovieNotFoundException e, HttpServletRequest request) {
        return new ErrorResponse(request.getRequestURI(), e.getMessage(), Instant.now());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        return new ErrorResponse(request.getRequestURI(), "Произошла ошибка на сервере", Instant.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception e, HttpServletRequest request) {
        return new ErrorResponse(request.getRequestURI(), "Произошла ошибка на сервере", Instant.now());
    }
}
