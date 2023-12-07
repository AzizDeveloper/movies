package dev.aziz.movies.mappers;

import dev.aziz.movies.dtos.GenreDto;
import dev.aziz.movies.entities.Genre;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    GenreDto genreToGenreDto(Genre genre);
    Genre genreDtoToGenre(GenreDto genreDto);

    List<GenreDto> genreListToGenreDtoList(List<Genre> genreList);

    List<Genre> genreDtoListToGenreList(List<GenreDto> genreDtoList);

}
