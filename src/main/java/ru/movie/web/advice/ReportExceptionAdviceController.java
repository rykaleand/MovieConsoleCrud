package ru.movie.web.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.movie.exception.ReportNotFoundException;
import ru.movie.web.advice.model.ErrorResponse;

import java.time.Instant;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ReportExceptionAdviceController {

    @ExceptionHandler(ReportNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ErrorResponse handleReportNotFoundException(ReportNotFoundException e, HttpServletRequest request) {
        return new ErrorResponse(request.getRequestURI(), e.getMessage(), Instant.now());
    }
}
