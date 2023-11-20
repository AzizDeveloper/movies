package dev.aziz.movies.services;

import dev.aziz.movies.dtos.MovieDto;
import dev.aziz.movies.dtos.ReducedMovieDto;
import dev.aziz.movies.dtos.UpdateMovieDto;
import dev.aziz.movies.entities.Movie;
import dev.aziz.movies.mappers.MovieMapper;
import dev.aziz.movies.mappers.MovieMapperImpl;
import dev.aziz.movies.repositories.MovieRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @InjectMocks
    private MovieService movieService;

    @Mock
    private MovieRepository movieRepository;

    @Spy
    private static MovieMapper movieMapper;

    @BeforeAll
    public static void setUp() {
        movieMapper = new MovieMapperImpl();
    }

    @Test
    void getMoviesTest() {
        // given
        List<Movie> movieList = Arrays.asList(
                new Movie(1L, "Titanic", "TEST A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.", 1997, 5, "James Cameron", "Leonardo DiCaprio, Kate Winslet, Billy Zane, Kathy Bates, Frances Fisher", "Drama", Instant.now(), Instant.now()),
                new Movie(2L, "The Shawshank Redemption", "Over the course of several years, two convicts form a friendship, seeking consolation and, eventually, redemption through basic compassion.", 1994, 4, "Frank Darabont", "Tim Robbins, Morgan Freeman, Bob Gunton, William Sandler, Clancy Brown", "Drama", Instant.now(), Instant.now()),
                new Movie(3L, "The Godfather", "Don Vito Corleone, head of a mafia family, decides to hand over his empire to his youngest son Michael. However, his decision unintentionally puts the lives of his loved ones in grave danger.", 1972, 3, "Francis Ford Coppola", "Marlon Brando, Al Pacino, James Caan, Richard S. Castellano, Robert Duvall", "Crime", Instant.now(), Instant.now())
        );

        // when
        Mockito.when(movieRepository.findAll()).thenReturn(movieList);
        List<ReducedMovieDto> movies = movieService.getMovies();

        // then
        assertAll(() -> {
            assertEquals(3, movies.size());
            assertEquals(movieMapper.moviesToReducedMovieDtos(movieList), movies);
        });
    }

    @Test
    void findMovieByIdTest() {
        // given
        List<Movie> movieList = Arrays.asList(
                new Movie(1L, "Titanic", "TEST A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.", 1997, 5, "James Cameron", "Leonardo DiCaprio, Kate Winslet, Billy Zane, Kathy Bates, Frances Fisher", "Drama", Instant.now(), Instant.now()),
                new Movie(2L, "The Shawshank Redemption", "Over the course of several years, two convicts form a friendship, seeking consolation and, eventually, redemption through basic compassion.", 1994, 4, "Frank Darabont", "Tim Robbins, Morgan Freeman, Bob Gunton, William Sandler, Clancy Brown", "Drama", Instant.now(), Instant.now()),
                new Movie(3L, "The Godfather", "Don Vito Corleone, head of a mafia family, decides to hand over his empire to his youngest son Michael. However, his decision unintentionally puts the lives of his loved ones in grave danger.", 1972, 3, "Francis Ford Coppola", "Marlon Brando, Al Pacino, James Caan, Richard S. Castellano, Robert Duvall", "Crime", Instant.now(), Instant.now())
        );

        Mockito.when(movieRepository.findMovieById(1L)).thenReturn(Optional.ofNullable(movieList.get(0)));

        // when
        MovieDto foundMovie = movieService.findMovieById(1L);

        // then
        verify(movieRepository).findMovieById(1L);
        assertAll(() -> {
            assertEquals("Titanic", foundMovie.getTitle());
            assertEquals(1997, foundMovie.getProducedYear());
            assertEquals("James Cameron", foundMovie.getDirector());
        });
    }

    @Test
    void createMovieTest() {
        // given
        Movie movie = new Movie(4L, "The Dark Knight", "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.", 2008, 5, "Christopher Nolan", "Christian Bale, Heat Ledger, Aaron Eckhart, Michael Caine, Cillian Murphy", "Action", Instant.now(), Instant.now());

        Mockito.when(movieRepository.save(movie)).thenReturn(movie);
        // when
        MovieDto createdMovie = movieService.createMovie(movie);
        // then
        verify(movieRepository).save(movie);
        assertAll(() -> {
            assertEquals(createdMovie, movieMapper.movieToMovieDto(movie));
        });

    }

    @Test
    void deleteByIdTest() {
        // given
        Long id = 1L;
        Movie movie = new Movie(1L, "Titanic", "TEST A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.", 1997, 5, "James Cameron", "Leonardo DiCaprio, Kate Winslet, Billy Zane, Kathy Bates, Frances Fisher", "Drama", Instant.now(), Instant.now());

        Mockito.when(movieRepository.findMovieById(1L)).thenReturn(Optional.of(movie));
        // when
        MovieDto movieDto = movieService.deleteById(id);

        // then
        verify(movieRepository).deleteById(id);
        assertAll(() -> {
            assertEquals(movieMapper.movieToMovieDto(movie), movieDto);
        });
    }

    @Test
    void updateByIdTest() {
        // given
        Movie movie = new Movie(1L, "Titanic", "TEST A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.", 1997, 5, "James Cameron", "Leonardo DiCaprio, Kate Winslet, Billy Zane, Kathy Bates, Frances Fisher", "Drama", Instant.now(), Instant.now());

        UpdateMovieDto updateMovieDto1 = UpdateMovieDto
                .builder()
                .description("Just movie")
                .build();
        Movie updatedMovie = new Movie(1L, "Titanic", "TEST A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.", 1997, 5, "James Cameron", "Leonardo DiCaprio, Kate Winslet, Billy Zane, Kathy Bates, Frances Fisher", "Drama", Instant.now(), Instant.now());
        updatedMovie.setDescription(updateMovieDto1.getDescription());

        Mockito.when(movieRepository.findMovieById(1L)).thenReturn(Optional.of(movie));
        Mockito.when(movieRepository.save(any(Movie.class))).thenReturn(updatedMovie);

        //when
        MovieDto movieDto1 = movieService.updateById(1L, updateMovieDto1);

        // then
        verify(movieRepository).save(any(Movie.class));
        assertAll(() -> {
            assertEquals("Just movie", movieDto1.getDescription());
        });
    }

    @Test
    void updateFullMovieByIdTest() {
        // given
        Movie movie = new Movie(1L, "Titanic", "TEST A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.", 1997, 5, "James Cameron", "Leonardo DiCaprio, Kate Winslet, Billy Zane, Kathy Bates, Frances Fisher", "Drama", Instant.now(), Instant.now());

        UpdateMovieDto updateMovieDto1 = UpdateMovieDto
                .builder()
                .description("Just movie")
                .rate(2)
                .genre("Fantasy")
                .build();
        Movie updatedMovie = new Movie(1L, "Titanic", "TEST A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.", 1997, 5, "James Cameron", "Leonardo DiCaprio, Kate Winslet, Billy Zane, Kathy Bates, Frances Fisher", "Drama", Instant.now(), Instant.now());
        updatedMovie.setDescription(updateMovieDto1.getDescription());
        updatedMovie.setRate(updateMovieDto1.getRate());
        updatedMovie.setGenre(updateMovieDto1.getGenre());

        Mockito.when(movieRepository.findMovieById(1L)).thenReturn(Optional.of(movie));
        Mockito.when(movieRepository.save(any(Movie.class))).thenReturn(updatedMovie);

        //when
        MovieDto movieDto1 = movieService.updateById(1L, updateMovieDto1);

        // then
        verify(movieRepository).save(any(Movie.class));

        // then
        assertAll(() -> {
            assertEquals("Just movie", movieDto1.getDescription());
            assertEquals(2, movieDto1.getRate());
            assertEquals("Fantasy", movieDto1.getGenre());
        });
    }
}