package dev.aziz.movies.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.aziz.movies.dtos.MovieDto;
import dev.aziz.movies.dtos.ReducedMovieDto;
import dev.aziz.movies.dtos.UpdateMovieDto;
import dev.aziz.movies.entities.Movie;
import dev.aziz.movies.mappers.MovieMapper;
import dev.aziz.movies.mappers.MovieMapperImpl;
import dev.aziz.movies.services.MovieService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
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

    private static MovieMapper movieMapper;

    @BeforeAll
    public static void setUp() {
        movieMapper = new MovieMapperImpl();
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
                new Movie(1L, "Titanic", "TEST A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.", 1997, 5, "James Cameron", "Leonardo DiCaprio, Kate Winslet, Billy Zane, Kathy Bates, Frances Fisher", "Drama", Instant.now(), Instant.now()),
                new Movie(2L, "The Shawshank Redemption", "Over the course of several years, two convicts form a friendship, seeking consolation and, eventually, redemption through basic compassion.", 1994, 4, "Frank Darabont", "Tim Robbins, Morgan Freeman, Bob Gunton, William Sandler, Clancy Brown", "Drama", Instant.now(), Instant.now()),
                new Movie(3L, "The Godfather", "Don Vito Corleone, head of a mafia family, decides to hand over his empire to his youngest son Michael. However, his decision unintentionally puts the lives of his loved ones in grave danger.", 1972, 3, "Francis Ford Coppola", "Marlon Brando, Al Pacino, James Caan, Richard S. Castellano, Robert Duvall", "Crime", Instant.now(), Instant.now())
        );
        //when
        when(movieService.findMovieById(1L)).thenReturn(movieMapper.movieToMovieDto(movieList.get(0)));
        //then
        mockMvc.perform(get("/movies/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Titanic"))
                .andExpect(jsonPath("$.producedYear").value("1997"))
                .andExpect(jsonPath("$.genre").value("Drama"))
                .andExpect(jsonPath("$.director").value("James Cameron"));
    }

    @Test
    void createMovie() throws Exception {
        //given
        Movie movie = new Movie(4L, "The Dark Knight", "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.", 2008, 5, "Christopher Nolan", "Christian Bale, Heat Ledger, Aaron Eckhart, Michael Caine, Cillian Murphy", "Action", Instant.now(), Instant.now());

        String movieJson = objectMapper.writeValueAsString(movie);

        //when
        when(movieService.createMovie(movie)).thenReturn(movieMapper.movieToMovieDto(movie));

        //then
        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(movieJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("The Dark Knight"))
                .andExpect(jsonPath("$.director").value("Christopher Nolan"));

    }

    @Test
    void deleteMovie() throws Exception {
        //given
        List<Movie> movieList = Arrays.asList(
                new Movie(1L, "Titanic", "TEST A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.", 1997, 5, "James Cameron", "Leonardo DiCaprio, Kate Winslet, Billy Zane, Kathy Bates, Frances Fisher", "Drama", Instant.now(), Instant.now()),
                new Movie(2L, "The Shawshank Redemption", "Over the course of several years, two convicts form a friendship, seeking consolation and, eventually, redemption through basic compassion.", 1994, 4, "Frank Darabont", "Tim Robbins, Morgan Freeman, Bob Gunton, William Sandler, Clancy Brown", "Drama", Instant.now(), Instant.now()),
                new Movie(3L, "The Godfather", "Don Vito Corleone, head of a mafia family, decides to hand over his empire to his youngest son Michael. However, his decision unintentionally puts the lives of his loved ones in grave danger.", 1972, 3, "Francis Ford Coppola", "Marlon Brando, Al Pacino, James Caan, Richard S. Castellano, Robert Duvall", "Crime", Instant.now(), Instant.now())
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
                new Movie(1L, "Titanic", "TEST A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.", 1997, 5, "James Cameron", "Leonardo DiCaprio, Kate Winslet, Billy Zane, Kathy Bates, Frances Fisher", "Drama", Instant.now(), Instant.now()),
                new Movie(2L, "The Shawshank Redemption", "Over the course of several years, two convicts form a friendship, seeking consolation and, eventually, redemption through basic compassion.", 1994, 4, "Frank Darabont", "Tim Robbins, Morgan Freeman, Bob Gunton, William Sandler, Clancy Brown", "Drama", Instant.now(), Instant.now()),
                new Movie(3L, "The Godfather", "Don Vito Corleone, head of a mafia family, decides to hand over his empire to his youngest son Michael. However, his decision unintentionally puts the lives of his loved ones in grave danger.", 1972, 3, "Francis Ford Coppola", "Marlon Brando, Al Pacino, James Caan, Richard S. Castellano, Robert Duvall", "Crime", Instant.now(), Instant.now())
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
                .director("James Cameron")
                .mainActors("Leonardo DiCaprio, Kate Winslet, Billy Zane, Kathy Bates, Frances Fisher")
                .genre("Horror")
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
                new Movie(1L, "Titanic", "TEST A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.", 1997, 5, "James Cameron", "Leonardo DiCaprio, Kate Winslet, Billy Zane, Kathy Bates, Frances Fisher", "Drama", Instant.now(), Instant.now()),
                new Movie(2L, "The Shawshank Redemption", "Over the course of several years, two convicts form a friendship, seeking consolation and, eventually, redemption through basic compassion.", 1994, 4, "Frank Darabont", "Tim Robbins, Morgan Freeman, Bob Gunton, William Sandler, Clancy Brown", "Drama", Instant.now(), Instant.now()),
                new Movie(3L, "The Godfather", "Don Vito Corleone, head of a mafia family, decides to hand over his empire to his youngest son Michael. However, his decision unintentionally puts the lives of his loved ones in grave danger.", 1972, 3, "Francis Ford Coppola", "Marlon Brando, Al Pacino, James Caan, Richard S. Castellano, Robert Duvall", "Crime", Instant.now(), Instant.now())
        );

        //when
        //then
        UpdateMovieDto updateMovieDto = UpdateMovieDto.builder()
                .description("Batman")
                .rate(3)
                .genre("Horror")
                .build();

        MovieDto movieDto = MovieDto.builder()
                .id(1L)
                .title("Titanic")
                .description("Batman")
                .producedYear(1997)
                .rate(3)
                .director("James Cameron")
                .mainActors("Leonardo DiCaprio, Kate Winslet, Billy Zane, Kathy Bates, Frances Fisher")
                .genre("Horror")
                .build();
        String inputJson = objectMapper.writeValueAsString(updateMovieDto);

        when(movieService.updateFullMovieById(1L, updateMovieDto)).thenReturn(movieDto);

        mockMvc.perform(put("/movies/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(inputJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Batman"))
                .andExpect(jsonPath("$.genre").value("Horror"));
    }
}