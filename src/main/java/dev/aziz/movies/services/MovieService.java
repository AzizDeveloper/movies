package dev.aziz.movies.services;

import dev.aziz.movies.dtos.MovieDto;
import dev.aziz.movies.dtos.ReducedMovieDto;
import dev.aziz.movies.dtos.UpdateMovieDto;
import dev.aziz.movies.exceptions.AppException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Getter
public class MovieService {

    private List<MovieDto> movieDtoList = new ArrayList<>(Arrays.asList(
            new MovieDto(1L, "Titanic", "A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.", 1997, 5, "James Cameron", List.of("Leonardo DiCaprio", "Kate Winslet", "Billy Zane", "Kathy Bates", "Frances Fisher"), "Drama"),
            new MovieDto(2L, "The Shawshank Redemption", "Over the course of several years, two convicts form a friendship, seeking consolation and, eventually, redemption through basic compassion.", 1994, 4, "Frank Darabont", List.of("Tim Robbins", "Morgan Freeman", "Bob Gunton", "William Sandler", "Clancy Brown"), "Drama"),
            new MovieDto(3L, "The Godfather", "Don Vito Corleone, head of a mafia family, decides to hand over his empire to his youngest son Michael. However, his decision unintentionally puts the lives of his loved ones in grave danger.", 1972, 3, "Francis Ford Coppola", List.of("Marlon Brando", "Al Pacino", "James Caan", "Richard S. Castellano", "Robert Duvall"), "Crime")
    ));

    public List<ReducedMovieDto> getMovies() {
        List<ReducedMovieDto> movieList = movieDtoList.stream()
                .map(movieDto -> new ReducedMovieDto(
                        movieDto.getTitle(), movieDto.getYear(), movieDto.getRate()
                )).toList();
        return movieList;
    }

    public MovieDto findMovieById(Long id) {
        MovieDto foundMovie = movieDtoList
                .stream()
                .filter(movieDto -> movieDto.getId() == id)
                .findFirst()
                .orElseThrow(() -> new AppException("Movie not found.", HttpStatus.NOT_FOUND));
        return foundMovie;
    }

    public MovieDto createMovie(MovieDto movieDto) {
        if (movieDtoList.contains(movieDto)) {
            throw new AppException("Movie already exists.", HttpStatus.BAD_REQUEST);
        }
        MovieDto createdMovieDto = movieDto;
        movieDtoList.add(movieDto);
        return createdMovieDto;
    }

    public MovieDto deleteById(Long id) {
        MovieDto movieById = findMovieById(id);
        movieDtoList.remove(movieById);
        return movieById;
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
        return movieById;
    }

    public MovieDto updateFullMovieById(Long id, UpdateMovieDto updateMovieDto) {
        MovieDto movieById = findMovieById(id);
        movieById.setDescription(updateMovieDto.getDescription());
        movieById.setRate(updateMovieDto.getRate());
        movieById.setGenre(updateMovieDto.getGenre());
        return movieById;
    }
}
