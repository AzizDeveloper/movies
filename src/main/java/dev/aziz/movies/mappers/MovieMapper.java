package dev.aziz.movies.mappers;

import dev.aziz.movies.dtos.MovieDto;
import dev.aziz.movies.dtos.ReducedMovieDto;
import dev.aziz.movies.entities.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {GenreMapper.class, PersonMapper.class})
public interface MovieMapper {

    @Mapping(target = "directorDtos", source = "directors")
    @Mapping(target = "actorDtos", source = "mainActors")
    @Mapping(target = "genreDtos", source = "genres")
    MovieDto movieToMovieDto(Movie movie);

    @Mapping(target = "directors", source = "directorDtos")
    @Mapping(target = "mainActors", source = "actorDtos")
    @Mapping(target = "genres", source = "genreDtos")
    Movie movieDtoToMovie(MovieDto movieDto);

    ReducedMovieDto movieToReducedMovieDto(Movie movie);

    List<ReducedMovieDto> moviesToReducedMovieDtos(List<Movie> movies);

}
