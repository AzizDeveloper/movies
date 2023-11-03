package dev.aziz.movies.services;

import dev.aziz.movies.dtos.MovieDto;
import dev.aziz.movies.dtos.ReducedMovieDto;
import dev.aziz.movies.dtos.UpdateDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void createMovieTest() {
        // given
        MovieDto movie = new MovieDto(4L, "The Dark Knight", "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.\n", 2008, 5, "Christopher Nolan", List.of("Christian Bale", "Heat Ledger", "Aaron Eckhart", "Michael Caine", "Cillian Murphy"), "Action");

        // when
        // then
        assertEquals(movieService.createMovie(movie), movie);
    }

    @Test
    void deleteByIdTest() {
        // given
        List<MovieDto> defaultInMemory = new ArrayList<>(Arrays.asList(
                new MovieDto(1L, "Titanic", "A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.", 1997, 5, "James Cameron", List.of("Leonardo DiCaprio", "Kate Winslet", "Billy Zane", "Kathy Bates", "Frances Fisher"), "Drama"),
                new MovieDto(2L, "The Shawshank Redemption", "Over the course of several years, two convicts form a friendship, seeking consolation and, eventually, redemption through basic compassion.", 1994, 4, "Frank Darabont", List.of("Tim Robbins", "Morgan Freeman", "Bob Gunton", "William Sandler", "Clancy Brown"), "Drama"),
                new MovieDto(3L, "The Godfather", "Don Vito Corleone, head of a mafia family, decides to hand over his empire to his youngest son Michael. However, his decision unintentionally puts the lives of his loved ones in grave danger.", 1972, 3, "Francis Ford Coppola", List.of("Marlon Brando", "Al Pacino", "James Caan", "Richard S. Castellano", "Robert Duvall"), "Crime")
        ));

        // when
        movieService.deleteById(1L);

        // then
        assertAll(() -> {
            assertEquals(2, movieService.getMovieDtoList().size());
            assertFalse(movieService.getMovieDtoList().contains(defaultInMemory.get(0)));
        });
    }

    @Test
    void updateByIdTest() {
        // given
        List<MovieDto> defaultInMemory = new ArrayList<>(Arrays.asList(
                new MovieDto(1L, "Titanic", "A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.", 1997, 5, "James Cameron", List.of("Leonardo DiCaprio", "Kate Winslet", "Billy Zane", "Kathy Bates", "Frances Fisher"), "Drama"),
                new MovieDto(2L, "The Shawshank Redemption", "Over the course of several years, two convicts form a friendship, seeking consolation and, eventually, redemption through basic compassion.", 1994, 4, "Frank Darabont", List.of("Tim Robbins", "Morgan Freeman", "Bob Gunton", "William Sandler", "Clancy Brown"), "Drama"),
                new MovieDto(3L, "The Godfather", "Don Vito Corleone, head of a mafia family, decides to hand over his empire to his youngest son Michael. However, his decision unintentionally puts the lives of his loved ones in grave danger.", 1972, 3, "Francis Ford Coppola", List.of("Marlon Brando", "Al Pacino", "James Caan", "Richard S. Castellano", "Robert Duvall"), "Crime")
        ));

        // when
        UpdateDto updateDto1 = UpdateDto
                .builder()
                .description("Just movie")
                .build();

        UpdateDto updateDto2 = UpdateDto
                .builder()
                .rate(3)
                .build();

        UpdateDto updateDto3 = UpdateDto
                .builder()
                .genre("Drama")
                .build();
        movieService.updateById(1L, updateDto1);
        movieService.updateById(2L, updateDto2);
        movieService.updateById(3L, updateDto3);

        // then
        assertAll(() -> {
            assertEquals("Just movie", movieService.getMovieDtoList().get(0).getDescription());
            assertEquals(3, movieService.getMovieDtoList().get(1).getRate());
            assertEquals("Drama", movieService.getMovieDtoList().get(2).getGenre());
        });
    }
}