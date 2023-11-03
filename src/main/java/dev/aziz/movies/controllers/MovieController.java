package dev.aziz.movies.controllers;

import dev.aziz.movies.dtos.MovieDto;
import dev.aziz.movies.dtos.ReducedMovieDto;
import dev.aziz.movies.dtos.UpdateDto;
import dev.aziz.movies.services.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/movies")
    public ResponseEntity<List<ReducedMovieDto>> getAllMovies() {
        return new ResponseEntity<>(movieService.getMovies(), HttpStatus.OK);
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<MovieDto> getMovie(@PathVariable Long id) {
        return new ResponseEntity<>(movieService.findMovieById(id), HttpStatus.OK);
    }

    @PostMapping("/movies")
    public ResponseEntity<MovieDto> createMovie(@RequestBody MovieDto movieDto) {
        return new ResponseEntity<>(movieService.createMovie(movieDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/movies/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable Long id) {
        movieService.deleteById(id);
        return new ResponseEntity<>("Movie with id " + id + " deleted", HttpStatus.OK);
    }

    @PatchMapping("/movies/{id}")
    public ResponseEntity<?> updateMovie(@PathVariable Long id, @RequestBody UpdateDto updateDto) {
        movieService.updateById(id, updateDto);
        return new ResponseEntity<>("Movie with id " + id + " updated", HttpStatus.OK);
    }
}
