package dev.aziz.movies.mappers;

import dev.aziz.movies.dtos.PersonDto;
import dev.aziz.movies.entities.Actor;
import dev.aziz.movies.entities.Director;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonDto actorToPersonDto(Actor actor);

    Actor personDtoToActor(PersonDto personDto);

    PersonDto directorToPersonDto(Director director);

    Director personDtoToDirector(PersonDto personDto);
}
