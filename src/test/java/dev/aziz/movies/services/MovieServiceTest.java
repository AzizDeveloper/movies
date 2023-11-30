package dev.aziz.movies.services;

import dev.aziz.movies.dtos.MovieDto;
import dev.aziz.movies.dtos.ReducedMovieDto;
import dev.aziz.movies.dtos.UpdateMovieDto;
import dev.aziz.movies.entities.Actor;
import dev.aziz.movies.entities.Director;
import dev.aziz.movies.entities.Genre;
import dev.aziz.movies.entities.Movie;
import dev.aziz.movies.mappers.GenreMapper;
import dev.aziz.movies.mappers.GenreMapperImpl;
import dev.aziz.movies.mappers.MovieMapper;
import dev.aziz.movies.mappers.MovieMapperImpl;
import dev.aziz.movies.mappers.PersonMapper;
import dev.aziz.movies.mappers.PersonMapperImpl;
import dev.aziz.movies.repositories.ActorRepository;
import dev.aziz.movies.repositories.DirectorRepository;
import dev.aziz.movies.repositories.GenreRepository;
import dev.aziz.movies.repositories.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @InjectMocks
    private MovieService movieService;

    @Spy
    private MovieMapper movieMapper = new MovieMapperImpl();

    @Spy
    private PersonMapper personMapper = new PersonMapperImpl();

    @Spy
    private GenreMapper genreMapper = new GenreMapperImpl();

    @Mock
    private MovieRepository movieRepository;
    @Mock
    private GenreRepository genreRepository;
    @Mock
    private ActorRepository actorRepository;
    @Mock
    private DirectorRepository directorRepository;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(movieMapper, "personMapper", personMapper);
        ReflectionTestUtils.setField(movieMapper, "genreMapper", genreMapper);
    }

    @Test
    void getMoviesTest() {
        // given
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

        Mockito.when(movieRepository.findMovieById(1L)).thenReturn(Optional.ofNullable(movieList.get(0)));

        // when
        MovieDto foundMovieDto = movieService.findMovieById(1L);
        System.out.println(foundMovieDto);

        // then
        verify(movieRepository).findMovieById(1L);
        assertAll(() -> {
            assertEquals("Titanic", foundMovieDto.getTitle());
            assertEquals(1997, foundMovieDto.getProducedYear());
            assertEquals("James", foundMovieDto.getDirectorDtos().get(0).getFirstName());
            assertEquals("Cameron", foundMovieDto.getDirectorDtos().get(0).getLastName());
        });
    }

    @Test
    void createMovieTest() {
        // Given
        Movie movie = new Movie(1L, "Titanic", "A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.", 1997, 5,
                List.of(new Director(1L, "James", "Cameron")),
                Arrays.asList(new Actor(1L, "Leonardo", "DiCaprio"),
                        new Actor(2L, "Kate", "Winslet"),
                        new Actor(3L, "Billy", "Zane"),
                        new Actor(4L, "Kathy", "Bates"),
                        new Actor(5L, "Frances", "Fisher")
                ),
                List.of(new Genre(1L, "Drama"), new Genre(2L, "Romance")), Instant.now(), Instant.now());

        Movie savedMovie = movie;
        savedMovie.setId(1L);

        Mockito.when(movieRepository.existsMovieByTitle(movie.getTitle())).thenReturn(false);
        Mockito.when(actorRepository.findActorByFirstNameAndLastName("Leonardo", "DiCaprio")).thenReturn(Optional.ofNullable(movie.getMainActors().get(0)));
        Mockito.when(directorRepository.findDirectorByFirstNameAndLastName("James", "Cameron")).thenReturn(Optional.ofNullable(movie.getDirectors().get(0)));
        Mockito.when(movieRepository.save(any(Movie.class))).thenReturn(savedMovie);

        // When
        MovieDto createdMovie = movieService.createMovie(movieMapper.movieToMovieDto(movie));

        // Then
        verify(movieRepository).save(any(Movie.class));
        assertAll(() -> {
            assertEquals(createdMovie, movieMapper.movieToMovieDto(savedMovie));
        });
    }

    @Test
    void deleteByIdTest() {
        // given
        Long id = 1L;
        Movie movie = new Movie(1L, "Titanic", "A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.", 1997, 5,
                List.of(new Director(1L, "James", "Cameron")),
                Arrays.asList(new Actor(1L, "Leonardo", "DiCaprio"),
                        new Actor(2L, "Kate", "Winslet"),
                        new Actor(3L, "Billy", "Zane"),
                        new Actor(4L, "Kathy", "Bates"),
                        new Actor(5L, "Frances", "Fisher")
                ),
                List.of(new Genre(1L, "Drama"), new Genre(2L, "Romance")), Instant.now(), Instant.now());

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
        Movie movie = new Movie(1L, "Titanic", "A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.", 1997, 5,
                List.of(new Director(1L, "James", "Cameron")),
                Arrays.asList(new Actor(1L, "Leonardo", "DiCaprio"),
                        new Actor(2L, "Kate", "Winslet"),
                        new Actor(3L, "Billy", "Zane"),
                        new Actor(4L, "Kathy", "Bates"),
                        new Actor(5L, "Frances", "Fisher")
                ),
                List.of(new Genre(1L, "Drama"), new Genre(2L, "Romance")), Instant.now(), Instant.now());

        UpdateMovieDto updateMovieDto1 = UpdateMovieDto
                .builder()
                .description("Just movie")
                .build();
        Movie updatedMovie = new Movie(1L, "Titanic", "A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.", 1997, 5,
                List.of(new Director(1L, "James", "Cameron")),
                Arrays.asList(new Actor(1L, "Leonardo", "DiCaprio"),
                        new Actor(2L, "Kate", "Winslet"),
                        new Actor(3L, "Billy", "Zane"),
                        new Actor(4L, "Kathy", "Bates"),
                        new Actor(5L, "Frances", "Fisher")
                ),
                List.of(new Genre(1L, "Drama"), new Genre(2L, "Romance")), Instant.now(), Instant.now());
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
        Movie movie = new Movie(1L, "Titanic", "A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.", 1997, 5,
                List.of(new Director(1L, "James", "Cameron")),
                Arrays.asList(new Actor(1L, "Leonardo", "DiCaprio"),
                        new Actor(2L, "Kate", "Winslet"),
                        new Actor(3L, "Billy", "Zane"),
                        new Actor(4L, "Kathy", "Bates"),
                        new Actor(5L, "Frances", "Fisher")
                ),
                List.of(new Genre(1L, "Drama"), new Genre(2L, "Romance")), Instant.now(), Instant.now());

        UpdateMovieDto updateMovieDto1 = UpdateMovieDto
                .builder()
                .description("Just movie")
                .rate(2)
                .genres(List.of(new Genre(3L, "Crime")))
                .build();
        Movie updatedMovie = new Movie(1L, "Titanic", "A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.", 1997, 5,
                List.of(new Director(1L, "James", "Cameron")),
                Arrays.asList(new Actor(1L, "Leonardo", "DiCaprio"),
                        new Actor(2L, "Kate", "Winslet"),
                        new Actor(3L, "Billy", "Zane"),
                        new Actor(4L, "Kathy", "Bates"),
                        new Actor(5L, "Frances", "Fisher")
                ),
                List.of(new Genre(1L, "Drama"), new Genre(2L, "Romance")), Instant.now(), Instant.now());
        updatedMovie.setDescription(updateMovieDto1.getDescription());
        updatedMovie.setRate(updateMovieDto1.getRate());
        updatedMovie.setGenres(updateMovieDto1.getGenres());

        Mockito.when(movieRepository.findMovieById(1L)).thenReturn(Optional.of(movie));
        Mockito.when(movieRepository.save(any(Movie.class))).thenReturn(updatedMovie);
        Mockito.when(genreRepository.findGenreByName("Crime")).thenReturn(Optional.of(new Genre(3L, "Crime")));

        //when
        MovieDto movieDto1 = movieService.updateById(1L, updateMovieDto1);

        // then
        verify(movieRepository).save(any(Movie.class));

        // then
        assertAll(() -> {
            assertEquals("Just movie", movieDto1.getDescription());
            assertEquals(2, movieDto1.getRate());
            assertEquals("Crime", movieDto1.getGenreDtos().get(0).getName());
        });
    }

    @Test
    void searchPersonTest() {
        //given
        String name = "kate";
        int pageNumber = 0;
        int size = 1;

        PageRequest pageRequest = PageRequest.of(pageNumber, size);

        List<String> names = new ArrayList<>();
        names.add("titanic");

        Page<String> pageOfNames = new PageImpl<>(names);
        Mockito.when(movieRepository.searchMoviesWithThePerson(name, pageRequest)).thenReturn(pageOfNames);

        //when
        Page<String> listOfNames = movieService.searchMoviesWithThePerson(name, pageRequest);

        //then
        assertAll(() -> {
            assertNotNull(listOfNames);
            assertEquals(listOfNames.getContent().get(0).toLowerCase(), "titanic");
        });
    }
}