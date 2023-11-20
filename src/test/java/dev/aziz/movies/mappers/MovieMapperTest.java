package dev.aziz.movies.mappers;

import dev.aziz.movies.dtos.MovieDto;
import dev.aziz.movies.dtos.ReducedMovieDto;
import dev.aziz.movies.entities.Movie;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieMapperTest {


    private static MovieMapper movieMapper;

    @BeforeAll
    public static void setUp() {
        movieMapper = new MovieMapperImpl();
    }
    @Test
    void movieToMovieDtoTest() {
        //given
        Movie movie = new Movie(1L, "Titanic", "TEST A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.", 1997, 5, "James Cameron", "Leonardo DiCaprio, Kate Winslet, Billy Zane, Kathy Bates, Frances Fisher", "Drama", Instant.now(), Instant.now());

        //when
        MovieDto movieDto = movieMapper.movieToMovieDto(movie);

        //then
        assertAll(() ->{
           assertEquals(movie.getTitle(), movieDto.getTitle());
           assertEquals(movie.getDescription(), movieDto.getDescription());
        });
    }

    @Test
    void movieDtoToMovie() {
        //given
        MovieDto movieDto = new MovieDto(1L, "Titanic", "TEST A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.", 1997, 5, "James Cameron", "Leonardo DiCaprio, Kate Winslet, Billy Zane, Kathy Bates, Frances Fisher", "Drama", Instant.now(), Instant.now());

        //when
        Movie movie = movieMapper.movieDtoToMovie(movieDto);

        //then
        assertAll(() ->{
            assertEquals(movieDto.getTitle(), movie.getTitle());
            assertEquals(movieDto.getDescription(), movie.getDescription());
        });
    }

    @Test
    void moviesToReducedMovieDtos() {
        //given
        List<Movie> movieList = Arrays.asList(
                new Movie(1L, "Titanic", "TEST A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.", 1997, 5, "James Cameron", "Leonardo DiCaprio, Kate Winslet, Billy Zane, Kathy Bates, Frances Fisher", "Drama", Instant.now(), Instant.now()),
                new Movie(2L, "The Shawshank Redemption", "Over the course of several years, two convicts form a friendship, seeking consolation and, eventually, redemption through basic compassion.", 1994, 4, "Frank Darabont", "Tim Robbins, Morgan Freeman, Bob Gunton, William Sandler, Clancy Brown", "Drama", Instant.now(), Instant.now()),
                new Movie(3L, "The Godfather", "Don Vito Corleone, head of a mafia family, decides to hand over his empire to his youngest son Michael. However, his decision unintentionally puts the lives of his loved ones in grave danger.", 1972, 3, "Francis Ford Coppola", "Marlon Brando, Al Pacino, James Caan, Richard S. Castellano, Robert Duvall", "Crime", Instant.now(), Instant.now())
        );

        //when
        List<ReducedMovieDto> reducedMovieDtos = movieMapper.moviesToReducedMovieDtos(movieList);

        //then
        assertAll(() ->{
            assertEquals(movieList.size(),reducedMovieDtos.size());
            assertEquals(movieList.get(0).getTitle(), reducedMovieDtos.get(0).getTitle());
        });
    }
}
