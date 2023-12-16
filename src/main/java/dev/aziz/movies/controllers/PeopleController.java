package dev.aziz.movies.controllers;

import dev.aziz.movies.dtos.PersonDto;
import dev.aziz.movies.entities.People;
import dev.aziz.movies.services.PeopleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "People endpoints")
public class PeopleController {

    private final PeopleService peopleService;

    @Operation(
            summary = "Get all people whether actor or director."
    )
    @GetMapping("/people")
    public ResponseEntity<List<String>> getAllPeople() {
        return ResponseEntity.ok(peopleService.getAllPeople());
    }

    @Operation(
            summary = "Get one person. First variable is actor or director then its id.",
            description = "PersonDTO - private Long id, String firstName, String lastName, List of GenreDto genresDto; GenreDto - Long id, String name"
    )
    @GetMapping("/people/{person}/{id}")
    public ResponseEntity<PersonDto> getOnePerson(@PathVariable People person, @PathVariable Long id) {
        return ResponseEntity.ok(peopleService.getOnePerson(person, id));
    }
}
