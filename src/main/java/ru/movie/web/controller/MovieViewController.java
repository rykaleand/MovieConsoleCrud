package ru.movie.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.movie.repository.MovieRepository;

@Controller
@RequestMapping("/view")
@RequiredArgsConstructor
public class MovieViewController {

    private final MovieRepository movieRepository;

    @GetMapping("/movies")
    public String movieList(Model model) {
        model.addAttribute("movies", movieRepository.findAll());
        return "movies";
    }
}