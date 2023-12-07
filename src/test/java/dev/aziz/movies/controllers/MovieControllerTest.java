package dev.aziz.movies.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.aziz.movies.dtos.GenreDto;
import dev.aziz.movies.dtos.MovieDto;
import dev.aziz.movies.dtos.PersonDto;
import dev.aziz.movies.dtos.ReducedMovieDto;
import dev.aziz.movies.dtos.SearchDto;
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
import dev.aziz.movies.services.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieController.class)
class MovieControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    public MovieService movieService;

    @Autowired
    public ObjectMapper objectMapper;


    private MovieMapper movieMapper = new MovieMapperImpl();

    private GenreMapper genreMapper = new GenreMapperImpl();

    private PersonMapper personMapper = new PersonMapperImpl();

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(movieMapper, "personMapper", personMapper);
        ReflectionTestUtils.setField(movieMapper, "genreMapper", genreMapper);
    }

    @Test
    void getMoviesTest() throws Exception {
        //given
        List<ReducedMovieDto> reducedMovieDtoList = List.of(
                new ReducedMovieDto("Titanic", 1997, 5),
                new ReducedMovieDto("The Shawshank Redemption", 1994, 4),
                new ReducedMovieDto("The Godfather", 1972, 3)
        );

        //when
        when(movieService.getMovies()).thenReturn(reducedMovieDtoList);

        //then
        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Titanic"))
                .andExpect(jsonPath("$[1].title").value("The Shawshank Redemption"))
                .andExpect(jsonPath("$[2].title").value("The Godfather"))
                .andExpect(jsonPath("$[0].producedYear").value(1997))
                .andExpect(jsonPath("$[1].producedYear").value(1994))
                .andExpect(jsonPath("$[2].producedYear").value(1972));
    }

    @Test
    void getMovieTest() throws Exception {
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
        when(movieService.findMovieById(1L)).thenReturn(movieMapper.movieToMovieDto(movieList.get(0)));
        //then
        mockMvc.perform(get("/movies/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Titanic"))
                .andExpect(jsonPath("$.producedYear").value("1997"))
                .andExpect(jsonPath("$.genreDtos[0].name").value("Drama"))
                .andExpect(jsonPath("$.directorDtos[0].firstName").value("James"));
    }

    @Test
    void createMovie() throws Exception {
        //given
        Movie movie = new Movie(4L, "The Dark Knight",
                "DARK KNIGHT TEST A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.",
                1997, 5,
                List.of(new Director(1L, "James", "Cameron")),
                Arrays.asList(
                        new Actor(1L, "Leonardo", "DiCaprio"),
                        new Actor(2L, "Kate", "Winslet"),
                        new Actor(3L, "Billy", "Zane"),
                        new Actor(4L, "Kathy", "Bates"),
                        new Actor(5L, "Frances", "Fisher")),
                List.of(new Genre(1L, "Drama")), Instant.now(), Instant.now());

        String movieJson = objectMapper.writeValueAsString(movieMapper.movieToMovieDto(movie));

        //when
        when(movieService.createMovie(movieMapper.movieToMovieDto(movie))).thenReturn(movieMapper.movieToMovieDto(movie));

        //then
        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(movieJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("The Dark Knight"))
                .andExpect(jsonPath("$.producedYear").value(1997));
    }

    @Test
    void deleteMovie() throws Exception {
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
        when(movieService.findMovieById(1L)).thenReturn(movieMapper.movieToMovieDto(movieList.get(0)));

        mockMvc.perform(get("/movies/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Titanic"))
                .andExpect(jsonPath("$.producedYear").value(1997));
    }

    @Test
    void updateMovie() throws Exception {
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
        //then
        UpdateMovieDto updateMovieDto = UpdateMovieDto.builder()
                .description("Batman")
                .rate(3)
                .build();

        MovieDto movieDto = MovieDto.builder()
                .id(1L)
                .title("Titanic")
                .description("Batman")
                .producedYear(1997)
                .rate(3)
                .directorDtos(List.of(new PersonDto(1L, "James", "Cameron")))
                .actorDtos(
                        Arrays.asList(new PersonDto(1L, "Leonardo", "DiCaprio"),
                                new PersonDto(2L, "Kate", "Winslet"),
                                new PersonDto(3L, "Billy", "Zane"),
                                new PersonDto(4L, "Kathy", "Bates"),
                                new PersonDto(5L, "Frances", "Fisher")
                        )
                )
                .genreDtos(List.of(new GenreDto(1L, "Drama")))
                .build();
        String inputJson = objectMapper.writeValueAsString(updateMovieDto);

        when(movieService.updateById(1L, updateMovieDto)).thenReturn(movieDto);

        mockMvc.perform(patch("/movies/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(inputJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Batman"))
                .andExpect(jsonPath("$.rate").value(3));
    }

    @Test
    void updateFullMovie() throws Exception {
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
        //then
        UpdateMovieDto updateMovieDto = UpdateMovieDto.builder()
                .description("Batman")
                .rate(3)
                .genres(List.of(new Genre(3L, "Crime")))
                .build();

        MovieDto movieDto = MovieDto.builder()
                .id(1L)
                .title("Titanic")
                .description("Batman")
                .producedYear(1997)
                .rate(3)
                .directorDtos(List.of(new PersonDto(1L, "James", "Cameron")))
                .actorDtos(
                        Arrays.asList(new PersonDto(1L, "Leonardo", "DiCaprio"),
                                new PersonDto(2L, "Kate", "Winslet"),
                                new PersonDto(3L, "Billy", "Zane"),
                                new PersonDto(4L, "Kathy", "Bates"),
                                new PersonDto(5L, "Frances", "Fisher")
                        )
                )
                .genreDtos(List.of(new GenreDto(3L, "Crime")))
                .build();
        String inputJson = objectMapper.writeValueAsString(updateMovieDto);

        when(movieService.updateFullMovieById(1L, updateMovieDto)).thenReturn(movieDto);

        mockMvc.perform(put("/movies/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(inputJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Batman"))
                .andExpect(jsonPath("$.genreDtos[0].name").value("Crime"));
    }

    @Test
    void searchMoviesWithThePersonTest() throws Exception {
        //given
        String name = "kate";
        int pageNumber = 0;
        int size = 1;
        SearchDto searchDto = SearchDto.builder().name(name).build();

        String inputJson = objectMapper.writeValueAsString(searchDto);
        PageRequest pageRequest = PageRequest.of(pageNumber, size);

        List<String> peopleList = new ArrayList<>();
        peopleList.add("titanic");
        Page<String> pageOfNames = new PageImpl<>(peopleList);

        when(movieService.searchMoviesWithThePerson(name, pageRequest)).thenReturn(pageOfNames);

        //when
        //then
        mockMvc.perform(post("/movies/search?page={pageNumber}&size={size}", pageNumber, size)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(inputJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0]").value("titanic"))
                .andExpect(jsonPath("$.last").value(true))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.size").value(1));
    }
}