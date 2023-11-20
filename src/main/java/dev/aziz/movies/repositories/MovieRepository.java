package dev.aziz.movies.repositories;

import dev.aziz.movies.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Optional<Movie> findMovieById(Long id);

    boolean existsMovieByTitle(String title);

    Optional<Movie> findMovieByTitle(String title);
}
