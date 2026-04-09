package ru.movie.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MovieCreateCommand {
    private String title;
    private String genre;
    private Integer releaseYear;
}