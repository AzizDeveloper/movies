package dev.aziz.movies.controllers;

import dev.aziz.movies.dtos.MovieDto;
import dev.aziz.movies.dtos.SearchDto;
import dev.aziz.movies.dtos.ReducedMovieDto;
import dev.aziz.movies.dtos.UpdateMovieDto;
import dev.aziz.movies.services.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Movie endpoints")
public class MovieController {

    private final MovieService movieService;

    @Operation(
            description = "Get all movies. Movies are reduced which has 3 fields.",
            summary = "Get all movies."
    )
    @GetMapping("/movies")
    public ResponseEntity<List<ReducedMovieDto>> getAllMovies() {
        return ResponseEntity.ok(movieService.getMovies());
    }

    @Operation(
            summary = "Get a movie by id."
    )
    @GetMapping("/movies/{id}")
    public ResponseEntity<MovieDto> getMovie(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.findMovieById(id));
    }

    @Operation(
            summary = "Create a movie."
    )
    @PostMapping("/movies")
    public ResponseEntity<MovieDto> createMovie(@RequestBody @Valid MovieDto movieDto) {
        return ResponseEntity.created(URI.create("/movies/" + movieDto.getId()))
                .body(movieService.createMovie(movieDto));
    }

    @Operation(
            summary = "Delete a movie by id."
    )
    @DeleteMapping("/movies/{id}")
    public ResponseEntity<MovieDto> deleteMovie(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.deleteById(id));
    }

    @Operation(
            summary = "Update a movie. Optional 3 fields."
    )
    @PatchMapping("/movies/{id}")
    public ResponseEntity<MovieDto> updateMovie(@PathVariable Long id, @RequestBody UpdateMovieDto updateMovieDto) {
        return ResponseEntity.ok(movieService.updateById(id, updateMovieDto));
    }

    @Operation(
            summary = "Update a movie. Mandatory 3 fields."
    )
    @PutMapping("/movies/{id}")
    public ResponseEntity<MovieDto> updateFullMovie(@PathVariable Long id, @RequestBody @Valid UpdateMovieDto updateMovieDto) {
        return ResponseEntity.ok(movieService.updateFullMovieById(id, updateMovieDto));
    }

    @Operation(
            summary = "Search movies with Person played in those movies."
    )
    @PostMapping("/movies/search")
    public ResponseEntity<Page<String>> searchMoviesWithThePerson(@RequestParam int page, @RequestParam int size, @RequestBody SearchDto searchDto) {
        PageRequest pr = PageRequest.of(page, size);
        return ResponseEntity.ok(movieService.searchMoviesWithThePerson(searchDto.getName(), pr));
    }
}
