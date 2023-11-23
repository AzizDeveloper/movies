package dev.aziz.movies.repositories;

import dev.aziz.movies.entities.Actor;
import dev.aziz.movies.entities.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {

    Optional<Director> findDirectorByFirstNameAndLastName(String firstName, String lastName);
}
