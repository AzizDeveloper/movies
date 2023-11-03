package dev.aziz.movies.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.aziz.movies.dtos.MovieDto;
import dev.aziz.movies.dtos.ReducedMovieDto;
import dev.aziz.movies.services.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieController.class)
class MovieControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    public MovieService movieService;

    private ObjectMapper objectMapper = new ObjectMapper();

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
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Titanic"))
                .andExpect(jsonPath("$[0].year").value(1997));
    }

    @Test
    void getMovieTest() throws Exception {
        //given
        List<MovieDto> movieDtoList = List.of(
                new MovieDto(1L, "Titanic", "A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.", 1997, 5, "James Cameron", List.of("Leonardo DiCaprio", "Kate Winslet", "Billy Zane", "Kathy Bates", "Frances Fisher"), "Drama"),
                new MovieDto(2L, "The Shawshank Redemption", "Over the course of several years, two convicts form a friendship, seeking consolation and, eventually, redemption through basic compassion.", 1994, 4, "Frank Darabont", List.of("Tim Robbins", "Morgan Freeman", "Bob Gunton", "William Sandler", "Clancy Brown"), "Drama"),
                new MovieDto(3L, "The Godfather", "Don Vito Corleone, head of a mafia family, decides to hand over his empire to his youngest son Michael. However, his decision unintentionally puts the lives of his loved ones in grave danger.", 1972, 3, "Francis Ford Coppola", List.of("Marlon Brando", "Al Pacino", "James Caan", "Richard S. Castellano", "Robert Duvall"), "Crime")
        );

        //when
        when(movieService.findMovieById(1L)).thenReturn(movieDtoList.get(0));
        MvcResult mvcResult = mockMvc.perform(get("/movies/1")).andReturn();
        MovieDto resultMovieDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), MovieDto.class);

        //then
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(objectMapper.writeValueAsString(movieDtoList.get(0)), mvcResult.getResponse().getContentAsString());
        assertEquals("Titanic", resultMovieDto.getTitle());
        assertEquals(1997, resultMovieDto.getYear());
        assertEquals(5, resultMovieDto.getRate());

    }

    @Test
    void createMovie() throws Exception {
        //TODO: write these tests
        //given
        MovieDto movie = new MovieDto(4L, "The Dark Knight", "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.", 2008, 5, "Christopher Nolan", List.of("Christian Bale", "Heat Ledger", "Aaron Eckhart", "Michael Caine", "Cillian Murphy"), "Action");

        String movieJson = objectMapper.writeValueAsString(movie);

        //when
        when(movieService.createMovie(movie)).thenReturn(movie);

        //then
        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(movieJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.title").value("The Dark Knight"))
                .andExpect(jsonPath("$.director").value("Christopher Nolan"));

    }

    @Test
    void deleteMovie() throws Exception {
        //TODO: write these tests
        //given
        List<MovieDto> movieDtoList = List.of(
                new MovieDto(1L, "Titanic", "A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.", 1997, 5, "James Cameron", List.of("Leonardo DiCaprio", "Kate Winslet", "Billy Zane", "Kathy Bates", "Frances Fisher"), "Drama"),
                new MovieDto(2L, "The Shawshank Redemption", "Over the course of several years, two convicts form a friendship, seeking consolation and, eventually, redemption through basic compassion.", 1994, 4, "Frank Darabont", List.of("Tim Robbins", "Morgan Freeman", "Bob Gunton", "William Sandler", "Clancy Brown"), "Drama"),
                new MovieDto(3L, "The Godfather", "Don Vito Corleone, head of a mafia family, decides to hand over his empire to his youngest son Michael. However, his decision unintentionally puts the lives of his loved ones in grave danger.", 1972, 3, "Francis Ford Coppola", List.of("Marlon Brando", "Al Pacino", "James Caan", "Richard S. Castellano", "Robert Duvall"), "Crime")
        );

        //when
        //then
        MvcResult mvcResult = mockMvc.perform(delete("/movies/{id}", 1))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "Movie with id 1 deleted");

    }

    @Test
    void updateMovie() throws Exception {
        //given
        List<MovieDto> movieDtoList = new ArrayList<>(Arrays.asList(
                new MovieDto(1L, "Titanic", "A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.", 1997, 5, "James Cameron", List.of("Leonardo DiCaprio", "Kate Winslet", "Billy Zane", "Kathy Bates", "Frances Fisher"), "Drama"),
                new MovieDto(2L, "The Shawshank Redemption", "Over the course of several years, two convicts form a friendship, seeking consolation and, eventually, redemption through basic compassion.", 1994, 4, "Frank Darabont", List.of("Tim Robbins", "Morgan Freeman", "Bob Gunton", "William Sandler", "Clancy Brown"), "Drama"),
                new MovieDto(3L, "The Godfather", "Don Vito Corleone, head of a mafia family, decides to hand over his empire to his youngest son Michael. However, his decision unintentionally puts the lives of his loved ones in grave danger.", 1972, 3, "Francis Ford Coppola", List.of("Marlon Brando", "Al Pacino", "James Caan", "Richard S. Castellano", "Robert Duvall"), "Crime")
        ));

        //when
        //then
        MovieDto movieDto = MovieDto.builder()
                .description("Batman")
                .rate(3)
                .genre("Horror")
                .build();
        String inputJson = objectMapper.writeValueAsString(movieDto);

        MvcResult mvcResult = mockMvc.perform(
                        patch("/movies/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(inputJson))
                .andExpect(status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "Movie with id 1 updated");
    }
}