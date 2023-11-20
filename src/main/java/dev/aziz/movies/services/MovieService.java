package dev.aziz.movies.services;

import dev.aziz.movies.dtos.MovieDto;
import dev.aziz.movies.dtos.ReducedMovieDto;
import dev.aziz.movies.dtos.UpdateMovieDto;
import dev.aziz.movies.entities.Movie;
import dev.aziz.movies.exceptions.AppException;
import dev.aziz.movies.mappers.MovieMapper;
import dev.aziz.movies.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    public List<ReducedMovieDto> getMovies() {
        List<ReducedMovieDto> reducedMovieDtos = movieMapper.moviesToReducedMovieDtos(movieRepository.findAll());
        return reducedMovieDtos;
    }

    public MovieDto findMovieById(Long id) {
        MovieDto movieDto = movieMapper.movieToMovieDto(movieRepository.findMovieById(id)
                .orElseThrow(() -> new AppException("Movie not found.", HttpStatus.NOT_FOUND)));
        return movieDto;
    }

    public MovieDto createMovie(Movie movie) {
        if (movieRepository.existsMovieByTitle(movie.getTitle())) {
            throw new AppException("Movie already exists.", HttpStatus.BAD_REQUEST);
        }
        return movieMapper.movieToMovieDto(movieRepository.save(movie));
    }

    public MovieDto deleteById(Long id) {
        MovieDto movieDto = findMovieById(id);
        movieRepository.deleteById(id);
        return movieDto;
    }

    public MovieDto updateById(Long id, UpdateMovieDto updateMovieDto) {
        MovieDto movieById = findMovieById(id);

        if ("".equals(updateMovieDto.getDescription())) {
            throw new AppException("Description must not be empty.", HttpStatus.BAD_REQUEST);
        } else if (updateMovieDto.getDescription() != null) {
            movieById.setDescription(updateMovieDto.getDescription());

        }

        if (updateMovieDto.getRate() != 0) {
            if (updateMovieDto.getRate() > 0 && updateMovieDto.getRate() <= 5) {
                movieById.setRate(updateMovieDto.getRate());
            } else if (updateMovieDto.getRate() < 1 || updateMovieDto.getRate() > 5) {
                throw new AppException("Rate must not be empty and should be between 1 and 5.", HttpStatus.BAD_REQUEST);
            }
        }

        if ("".equals(updateMovieDto.getGenre())) {
            throw new AppException("Genre must not be empty.", HttpStatus.BAD_REQUEST);
        } else if (updateMovieDto.getGenre() != null) {
            movieById.setGenre(updateMovieDto.getGenre());
        }
        return movieMapper.movieToMovieDto(movieRepository.save(movieMapper.movieDtoToMovie(movieById)));
    }

    public MovieDto updateFullMovieById(Long id, UpdateMovieDto updateMovieDto) {
        MovieDto movieById = findMovieById(id);
        movieById.setDescription(updateMovieDto.getDescription());
        movieById.setRate(updateMovieDto.getRate());
        movieById.setGenre(updateMovieDto.getGenre());
        Movie save = movieRepository.save(movieMapper.movieDtoToMovie(movieById));
        return movieMapper.movieToMovieDto(save);
    }
}
