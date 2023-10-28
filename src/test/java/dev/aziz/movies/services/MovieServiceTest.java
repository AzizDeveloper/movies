package dev.aziz.movies.services;

import dev.aziz.movies.dtos.MovieDto;
import dev.aziz.movies.dtos.ReducedMovieDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    private MovieService movieService = new MovieService();

    @Test
    void getMoviesTest() {
        // given
        List<ReducedMovieDto> reducedMovieDtoList = List.of(
                new ReducedMovieDto("Titanic", 1997, 5),
                new ReducedMovieDto("The Shawshank Redemption", 1994, 4),
                new ReducedMovieDto("The Godfather", 1972, 3)
        );

        // when
        List<ReducedMovieDto> movies = movieService.getMovies();

        // then
        assertAll(() -> {
            assertEquals(3, movies.size());
            assertEquals(reducedMovieDtoList, movies);
        });
    }

    @Test
    void findMovieByIdTest() {
        // given
        MovieDto movie = new MovieDto(1L, "Titanic", "A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.", 1997, 5, "James Cameron", List.of("Leonardo DiCaprio", "Kate Winslet", "Billy Zane", "Kathy Bates", "Frances Fisher"), "Drama");

        // when
        MovieDto foundMovie = movieService.findMovieById(1L);

        // then
        assertAll(() -> {
            assertEquals("Titanic", foundMovie.getTitle());
            assertEquals(1997, foundMovie.getYear());
            assertEquals("James Cameron", foundMovie.getDirector());
        });
    }
}