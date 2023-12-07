package dev.aziz.movies.controllers;

import dev.aziz.movies.dtos.PersonDto;
import dev.aziz.movies.entities.People;
import dev.aziz.movies.services.PeopleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PeopleController.class)
public class PeopleControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    public PeopleService peopleService;

    @Test
    void getAllPeopleTest() throws Exception {
        //given
        List<String> names = Arrays.asList(
                "Leonardo DiCaprio",
                "Kate Winslet",
                "Billy Zane",
                "Kathy Bates",
                "Frances Fisher",
                "Tim Robbins",
                "Morgan Freeman",
                "Bob Gunton",
                "William Sandler",
                "Clancy Brown",
                "Marlon Brando",
                "Al Pacino",
                "James Caan",
                "Richard S. Castellano",
                "Robert Duvall",
                "James Cameron",
                "Frank Darabont",
                "Francis Coppola"
        );

        //when
        when(peopleService.getAllPeople()).thenReturn(names);

        //then
        mockMvc.perform(get("/people"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Leonardo DiCaprio"))
                .andExpect(jsonPath("$.length()").value(18))
                .andExpect(jsonPath("$[1]").value("Kate Winslet"));
    }

    @Test
    void getOnePersonActorTest() throws Exception {
        //given
        PersonDto leo = new PersonDto(1L, "Leonardo", "DiCaprio");
        People typeActor = People.ACTOR;

        //when
        when(peopleService.getOnePerson(typeActor, leo.getId())).thenReturn(leo);
        //then
        mockMvc.perform(get("/people/{type}/{id}", typeActor, leo.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("Leonardo"))
                .andExpect(jsonPath("$.lastName").value("DiCaprio"));
    }

    @Test
    void getOnePersonDirectorTest() throws Exception {
        //given
        PersonDto james = new PersonDto(1L, "James", "Cameron");
        People typeDirector = People.DIRECTOR;

        //when
        when(peopleService.getOnePerson(typeDirector, james.getId())).thenReturn(james);

        //then
        mockMvc.perform(get("/people/{type}/{id}", typeDirector, james.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("James"))
                .andExpect(jsonPath("$.lastName").value("Cameron"));
    }

}
