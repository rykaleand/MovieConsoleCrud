package ru.movie.web.advice.model;

import java.time.Instant;

public record ErrorResponse(String path, String message, Instant timestamp) {
}