package dev.aziz.movies.mappers;

import dev.aziz.movies.dtos.MovieDto;
import dev.aziz.movies.dtos.ReducedMovieDto;
import dev.aziz.movies.entities.Movie;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    MovieDto movieToMovieDto(Movie movie);

    Movie movieDtoToMovie(MovieDto movieDto);

    ReducedMovieDto movieToReducedMovieDto(Movie movie);

    List<ReducedMovieDto> moviesToReducedMovieDtos(List<Movie> movies);

}
