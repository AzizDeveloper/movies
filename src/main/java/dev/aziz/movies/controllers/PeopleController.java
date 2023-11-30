package dev.aziz.movies.controllers;

import dev.aziz.movies.dtos.PersonDto;
import dev.aziz.movies.entities.People;
import dev.aziz.movies.services.PeopleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PeopleController {

    private final PeopleService peopleService;

    @GetMapping("/people")
    public ResponseEntity<List<String>> getAllPeople() {
        return ResponseEntity.ok(peopleService.getAllPeople());
    }

    @GetMapping("/people/{person}/{id}")
    public ResponseEntity<PersonDto> getOnePerson(@PathVariable People person, @PathVariable Long id) {
        return ResponseEntity.ok(peopleService.getOnePerson(person, id));
    }
}
