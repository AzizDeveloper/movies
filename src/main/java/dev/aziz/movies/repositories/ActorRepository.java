package dev.aziz.movies.repositories;

import dev.aziz.movies.entities.Actor;
import dev.aziz.movies.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {

    Optional<Actor> findActorByFirstNameAndLastName(String firstName, String lastName);
}
