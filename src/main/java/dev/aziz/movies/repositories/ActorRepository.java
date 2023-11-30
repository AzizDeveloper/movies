package dev.aziz.movies.repositories;

import dev.aziz.movies.entities.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {

    @Query(nativeQuery = true,
            value = "SELECT * FROM actor WHERE LOWER(first_name) = LOWER(:first_name) AND LOWER(last_name) = LOWER(:last_name);"
    )
    Optional<Actor> findActorByFirstNameAndLastName(@Param("first_name") String firstName, @Param("last_name") String lastName);
}
