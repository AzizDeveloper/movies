package dev.aziz.movies.repositories;

import dev.aziz.movies.entities.Actor;
import dev.aziz.movies.entities.Director;
import dev.aziz.movies.entities.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Optional<Movie> findMovieById(Long id);

    boolean existsMovieByTitle(String title);

    Optional<Movie> findMovieByTitle(String title);

    @Query(nativeQuery = true,
            value = "SELECT movie.title\n" +
                    "FROM movie\n" +
                    "LEFT JOIN movie_main_actors ON movie.id = movie_main_actors.movie_id\n" +
                    "LEFT JOIN actor ON movie_main_actors.actor_id = actor.id\n" +
                    "LEFT JOIN movie_directors ON movie.id = movie_directors.movie_id\n" +
                    "LEFT JOIN director ON movie_directors.director_id = director.id\n" +
                    "WHERE actor.first_name ILIKE CONCAT('%', :name, '%') OR actor.last_name ILIKE CONCAT('%', :name, '%') \n" +
                    "OR director.first_name ILIKE CONCAT('%', :name, '%') OR director.last_name  ILIKE CONCAT('%', :name, '%');"
    )
    Page<String> searchMoviesWithThePerson(@Param("name") String name, PageRequest pageRequest);

    List<Movie> findByDirectors(Director director);

    List<Movie> findByMainActors(Actor actor);

}
