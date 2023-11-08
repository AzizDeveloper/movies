package dev.aziz.movies.controllers;

import dev.aziz.movies.dtos.MovieDto;
import dev.aziz.movies.dtos.ReducedMovieDto;
import dev.aziz.movies.dtos.UpdateMovieDto;
import dev.aziz.movies.services.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/movies")
    public ResponseEntity<List<ReducedMovieDto>> getAllMovies() {
        return ResponseEntity.ok(movieService.getMovies());
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<MovieDto> getMovie(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.findMovieById(id));
    }

    @PostMapping("/movies")
    public ResponseEntity<MovieDto> createMovie(@RequestBody @Valid MovieDto movieDto) {
        return ResponseEntity.created(URI.create("/movies")).body(movieService.createMovie(movieDto));
    }

    @DeleteMapping("/movies/{id}")
    public ResponseEntity<MovieDto> deleteMovie(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.deleteById(id));
    }

    @PatchMapping("/movies/{id}")
    public ResponseEntity<MovieDto> updateMovie(@PathVariable Long id, @RequestBody UpdateMovieDto updateMovieDto) {
        return ResponseEntity.ok(movieService.updateById(id, updateMovieDto));
    }

    @PutMapping("/movies/{id}")
    public ResponseEntity<MovieDto> updateFullMovie(@PathVariable Long id, @RequestBody @Valid UpdateMovieDto updateMovieDto) {
        return ResponseEntity.ok(movieService.updateFullMovieById(id, updateMovieDto));
    }
}
