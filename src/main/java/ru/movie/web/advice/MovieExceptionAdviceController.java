package ru.movie.web.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.movie.exception.MovieNotFoundException;
import ru.movie.web.advice.model.ErrorResponse;

import java.time.Instant;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MovieExceptionAdviceController {

    @ExceptionHandler(MovieNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleMovieNotFoundException(MovieNotFoundException e, HttpServletRequest request) {
        return new ErrorResponse(request.getRequestURI(), e.getMessage(), Instant.now());
    }
}
