package dev.aziz.movies.services;

import dev.aziz.movies.dtos.GenreDto;
import dev.aziz.movies.dtos.PersonDto;
import dev.aziz.movies.entities.Actor;
import dev.aziz.movies.entities.Director;
import dev.aziz.movies.entities.Movie;
import dev.aziz.movies.entities.People;
import dev.aziz.movies.exceptions.AppException;
import dev.aziz.movies.mappers.GenreMapper;
import dev.aziz.movies.mappers.PersonMapper;
import dev.aziz.movies.repositories.ActorRepository;
import dev.aziz.movies.repositories.DirectorRepository;
import dev.aziz.movies.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class PeopleService {

    private final ActorRepository actorRepository;
    private final DirectorRepository directorRepository;
    private final MovieRepository movieRepository;
    private final GenreMapper genreMapper;
    private final PersonMapper personMapper;


    public List<String> getAllPeople() {
        List<String> listOfActors = actorRepository.findAll().stream()
                .map(actor -> actor.getFirstName() + " " + actor.getLastName())
                .toList();

        List<String> listOfDirectors = directorRepository.findAll().stream()
                .map(director -> director.getFirstName() + " " + director.getLastName())
                .toList();
        List<String> allPeople = new ArrayList<>();

        allPeople.addAll(listOfActors);
        allPeople.addAll(listOfDirectors);
        return allPeople;
    }


    public PersonDto getOnePerson(People person, Long id) {
        if (People.ACTOR.equals(person)) {
            Actor actor = actorRepository.findById(id).orElseThrow(() -> new AppException("Not found ", HttpStatus.BAD_REQUEST));
            Set<GenreDto> genresByActor = getGenresByActor(actor);
            PersonDto actorDto = personMapper.actorToPersonDto(actor);
            actorDto.setGenresDto(genresByActor.stream().toList());
            return actorDto;
        } else if (People.DIRECTOR.equals(person)) {
            Director director = directorRepository.findById(id).orElseThrow(() -> new AppException("Not found ", HttpStatus.BAD_REQUEST));
            Set<GenreDto> genresByDirector = getGenresByDirector(director);
            PersonDto directorDto = personMapper.directorToPersonDto(director);
            directorDto.setGenresDto(genresByDirector.stream().toList());
            return directorDto;
        }
        throw new AppException("Not found ", HttpStatus.BAD_REQUEST);
    }

    public Set<GenreDto> getGenresByDirector(Director director) {
        List<Movie> movies = movieRepository.findByDirectors(director);
        Set<GenreDto> genres = new HashSet<>();
        for (Movie movie : movies) {
            genres.addAll(genreMapper.genreListToGenreDtoList(movie.getGenres()));
        }
        return genres;
    }

    public Set<GenreDto> getGenresByActor(Actor actor) {
        List<Movie> movies = movieRepository.findByMainActors(actor);
        Set<GenreDto> genres = new HashSet<>();
        for (Movie movie : movies) {
            genres.addAll(genreMapper.genreListToGenreDtoList(movie.getGenres()));
        }
        return genres;
    }
}
