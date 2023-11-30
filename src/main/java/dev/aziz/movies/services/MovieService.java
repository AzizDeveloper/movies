package dev.aziz.movies.services;

import dev.aziz.movies.dtos.MovieDto;
import dev.aziz.movies.dtos.ReducedMovieDto;
import dev.aziz.movies.dtos.UpdateMovieDto;
import dev.aziz.movies.entities.Actor;
import dev.aziz.movies.entities.Director;
import dev.aziz.movies.entities.Genre;
import dev.aziz.movies.entities.Movie;
import dev.aziz.movies.exceptions.AppException;
import dev.aziz.movies.mappers.GenreMapper;
import dev.aziz.movies.mappers.MovieMapper;
import dev.aziz.movies.mappers.PersonMapper;
import dev.aziz.movies.repositories.ActorRepository;
import dev.aziz.movies.repositories.DirectorRepository;
import dev.aziz.movies.repositories.GenreRepository;
import dev.aziz.movies.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final ActorRepository actorRepository;
    private final DirectorRepository directorRepository;
    private final MovieMapper movieMapper;
    private final GenreMapper genreMapper;
    private final PersonMapper personMapper;

    public List<ReducedMovieDto> getMovies() {
        List<ReducedMovieDto> reducedMovieDtos = movieMapper.moviesToReducedMovieDtos(movieRepository.findAll());
        return reducedMovieDtos;
    }

    public MovieDto findMovieById(Long id) {
        MovieDto movieDto = movieMapper.movieToMovieDto(movieRepository.findMovieById(id)
                .orElseThrow(() -> new AppException("Movie not found.", HttpStatus.NOT_FOUND)));
        return movieDto;
    }

    public MovieDto createMovie(MovieDto movieDto) {
        if (movieRepository.existsMovieByTitle(movieDto.getTitle())) {
            throw new AppException("Movie already exists.", HttpStatus.BAD_REQUEST);
        }

        Movie movie = movieMapper.movieDtoToMovie(movieDto);
        List<Genre> genres = movieDto.getGenreDtos().stream()
                .map(genreDto -> {
                    Genre genre = genreMapper.genreDtoToGenre(genreDto);
                    return genreRepository.findGenreByName(genre.getName())
                            .orElseGet(() -> genreRepository.save(genre));
                }).collect(Collectors.toList());
        movie.setGenres(genres);

        List<Actor> actors = movieDto.getActorDtos().stream()
                .map(actorDto -> {
                    Actor actor = personMapper.personDtoToActor(actorDto);
                    return actorRepository.findActorByFirstNameAndLastName(actor.getFirstName(), actor.getLastName())
                            .orElseGet(() -> actorRepository.save(actor));
                }).collect(Collectors.toList());
        movie.setMainActors(actors);

        List<Director> directors = movieDto.getDirectorDtos().stream()
                .map(directorDto -> {
                    Director director = personMapper.personDtoToDirector(directorDto);
                    return directorRepository.findDirectorByFirstNameAndLastName(director.getFirstName(), director.getLastName())
                            .orElseGet(() -> directorRepository.save(director));
                }).collect(Collectors.toList());
        movie.setDirectors(directors);

        return movieMapper.movieToMovieDto(movieRepository.save(movie));
    }


    public MovieDto deleteById(Long id) {
        MovieDto movieDto = findMovieById(id);
        movieRepository.deleteById(id);
        return movieDto;
    }

    public MovieDto updateById(Long id, UpdateMovieDto updateMovieDto) {
        MovieDto movieDtoById = findMovieById(id);

        if ("".equals(updateMovieDto.getDescription())) {
            throw new AppException("Description must not be empty.", HttpStatus.BAD_REQUEST);
        } else if (updateMovieDto.getDescription() != null) {
            movieDtoById.setDescription(updateMovieDto.getDescription());

        }

        if (updateMovieDto.getRate() != 0) {
            if (updateMovieDto.getRate() > 0 && updateMovieDto.getRate() <= 5) {
                movieDtoById.setRate(updateMovieDto.getRate());
            } else if (updateMovieDto.getRate() < 1 || updateMovieDto.getRate() > 5) {
                throw new AppException("Rate must not be empty and should be between 1 and 5.", HttpStatus.BAD_REQUEST);
            }
        }

        if (updateMovieDto.getGenres() != null) {
            List<Genre> genreList = updateMovieDto.getGenres().stream()
                    .peek(genre -> {
                        if ("".equals(genre.getName())) {
                            throw new AppException("Genres should not be empty", HttpStatus.BAD_REQUEST);
                        }
                    })
                    .map(genre -> genreRepository.findGenreByName(genre.getName())
                            .orElseGet(() -> genreRepository.save(genre)))
                    .collect(Collectors.toList());

            movieDtoById.setGenreDtos(genreMapper.genreListToGenreDtoList(genreList));
        }
        return movieMapper.movieToMovieDto(movieRepository.save(movieMapper.movieDtoToMovie(movieDtoById)));
    }

    public MovieDto updateFullMovieById(Long id, UpdateMovieDto updateMovieDto) {
        MovieDto movieDtoById = findMovieById(id);
        List<Genre> genreList = updateMovieDto.getGenres().stream()
                .map(genre -> genreRepository.findGenreByName(genre.getName())
                        .orElseGet(() -> genreRepository.save(genre)))
                .collect(Collectors.toList());
        movieDtoById.setGenreDtos(genreMapper.genreListToGenreDtoList(genreList));
        movieDtoById.setDescription(updateMovieDto.getDescription());
        movieDtoById.setRate(updateMovieDto.getRate());
        Movie save = movieRepository.save(movieMapper.movieDtoToMovie(movieDtoById));
        return movieMapper.movieToMovieDto(save);
    }

    public Page<String> searchMoviesWithThePerson(String name, PageRequest pageRequest) {
        return movieRepository.searchMoviesWithThePerson(name, pageRequest);
    }
}
