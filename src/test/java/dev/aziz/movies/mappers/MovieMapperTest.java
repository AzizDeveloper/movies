package dev.aziz.movies.mappers;

import dev.aziz.movies.dtos.GenreDto;
import dev.aziz.movies.dtos.MovieDto;
import dev.aziz.movies.dtos.PersonDto;
import dev.aziz.movies.dtos.ReducedMovieDto;
import dev.aziz.movies.entities.Actor;
import dev.aziz.movies.entities.Director;
import dev.aziz.movies.entities.Genre;
import dev.aziz.movies.entities.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class MovieMapperTest {

    @Spy
    private MovieMapper movieMapper = new MovieMapperImpl();

    @Spy
    private PersonMapper personMapper = new PersonMapperImpl();

    @Spy
    private GenreMapper genreMapper = new GenreMapperImpl();

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(movieMapper, "personMapper", personMapper);
        ReflectionTestUtils.setField(movieMapper, "genreMapper", genreMapper);
    }

    @Test
    void movieToMovieDtoTest() {
        //given
        Movie movie = new Movie(
                1L,
                "Titanic",
                "TEST A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.",
                1997,
                5,
                List.of(new Director(1L, "James", "Cameron")),
                Arrays.asList(
                        new Actor(1L, "Leonardo", "DiCaprio"),
                        new Actor(2L, "Kate", "Winslet"),
                        new Actor(3L, "Billy", "Zane"),
                        new Actor(4L, "Kathy", "Bates"),
                        new Actor(5L, "Frances", "Fisher")
                ),
                List.of(new Genre(1L, "Drama")),
                Instant.now(),
                Instant.now()
        );

        //when
        MovieDto movieDto = movieMapper.movieToMovieDto(movie);

        //then
        assertAll(() -> {
            assertEquals(movie.getTitle(), movieDto.getTitle());
            assertEquals(movie.getDescription(), movieDto.getDescription());
        });
    }

    @Test
    void movieDtoToMovie() {
        //given
        MovieDto movieDto = new MovieDto(
                1L,
                "Titanic",
                "TEST A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.",
                1997,
                5,
                List.of(new PersonDto(1L, "James", "Cameron")),
                Arrays.asList(
                        new PersonDto(1L, "Leonardo", "DiCaprio"),
                        new PersonDto(2L, "Kate", "Winslet"),
                        new PersonDto(3L, "Billy", "Zane"),
                        new PersonDto(4L, "Kathy", "Bates"),
                        new PersonDto(5L, "Frances", "Fisher")
                ),
                List.of(new GenreDto(1L, "Drama")),
                Instant.now(),
                Instant.now()
        );

        //when
        Movie movie = movieMapper.movieDtoToMovie(movieDto);

        //then
        assertAll(() -> {
            assertEquals(movieDto.getTitle(), movie.getTitle());
            assertEquals(movieDto.getDescription(), movie.getDescription());
        });
    }

    @Test
    void moviesToReducedMovieDtos() {
        //given
        List<Movie> movieList = Arrays.asList(
                new Movie(1L, "Titanic", "A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.", 1997, 5,
                        List.of(new Director(1L, "James", "Cameron")),
                        Arrays.asList(new Actor(1L, "Leonardo", "DiCaprio"),
                                new Actor(2L, "Kate", "Winslet"),
                                new Actor(3L, "Billy", "Zane"),
                                new Actor(4L, "Kathy", "Bates"),
                                new Actor(5L, "Frances", "Fisher")
                        ),
                        List.of(new Genre(1L, "Drama"), new Genre(2L, "Romance")), Instant.now(), Instant.now()),

                new Movie(2L, "The Shawshank Redemption", "Over the course of several years, two convicts form a friendship, seeking consolation and, eventually, redemption through basic compassion.", 1994, 4,
                        List.of(new Director(2L, "Frank", "Darabont")),
                        Arrays.asList(new Actor(6L, "Tim", "Robbins"),
                                new Actor(7L, "Morgan", "Freeman"),
                                new Actor(8L, "Bob", "Gunton"),
                                new Actor(9L, "William", "Sandler"),
                                new Actor(10L, "Clancy", "Brown")
                        ),
                        List.of(new Genre(1L, "Drama")), Instant.now(), Instant.now()
                ),

                new Movie(3L, "The Godfather", "Don Vito Corleone, head of a mafia family, decides to hand over his empire to his youngest son Michael. However, his decision unintentionally puts the lives of his loved ones in grave danger.", 1972, 3,
                        List.of(new Director(3L, "Francis", "Coppola")),
                        Arrays.asList(
                                new Actor(11L, "Marlon", "Brando"),
                                new Actor(12L, "Al", "Pacino"),
                                new Actor(13L, "James", "Caan"),
                                new Actor(14L, "Richard", "Castellano"),
                                new Actor(15L, "Robert", "Duvall")
                        ),
                        List.of(new Genre(3L, "Crime")), Instant.now(), Instant.now()
                )
        );

        //when
        List<ReducedMovieDto> reducedMovieDtos = movieMapper.moviesToReducedMovieDtos(movieList);

        //then
        assertAll(() -> {
            assertEquals(movieList.size(), reducedMovieDtos.size());
            assertEquals(movieList.get(0).getTitle(), reducedMovieDtos.get(0).getTitle());
        });
    }
}
