package dev.aziz.movies.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.aziz.movies.dtos.MovieDto;
import dev.aziz.movies.dtos.ReducedMovieDto;
import dev.aziz.movies.services.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(MovieController.class)
class MovieControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    public MovieService movieService;

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getMoviesTest() throws Exception {
        //given
        List<ReducedMovieDto> reducedMovieDtoList = List.of(
                new ReducedMovieDto("Titanic", 1997, 5),
                new ReducedMovieDto("The Shawshank Redemption", 1994, 4),
                new ReducedMovieDto("The Godfather",1972, 3)
        );

        //when
        when(movieService.getMovies()).thenReturn(reducedMovieDtoList);
        MvcResult mvcResult = mockMvc.perform(get("/movies")).andReturn();

        //then
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(objectMapper.writeValueAsString(reducedMovieDtoList), mvcResult.getResponse().getContentAsString());

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
}