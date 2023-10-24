package dev.aziz.movies.controllers;

import dev.aziz.movies.dtos.MovieDto;
import dev.aziz.movies.dtos.ReducedMovieDto;
import dev.aziz.movies.services.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/movies")
    public List<ReducedMovieDto> getAllMovies() {
        List<MovieDto> movies = movieService.getMovies();
        List<ReducedMovieDto> reducedMovieDto = movies.stream()
                .map(movieDto -> new ReducedMovieDto(
                        movieDto.getTitle(), movieDto.getYear(), movieDto.getRate()
                )).toList();
        return reducedMovieDto;
    }

    @GetMapping("/movies/{id}")
    public MovieDto getMovie(@PathVariable Long id) {
        return movieService.findMovieById(id).orElseThrow();
    }
}
