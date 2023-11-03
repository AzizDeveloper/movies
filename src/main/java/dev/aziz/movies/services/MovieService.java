package dev.aziz.movies.services;

import dev.aziz.movies.dtos.MovieDto;
import dev.aziz.movies.dtos.ReducedMovieDto;
import dev.aziz.movies.dtos.UpdateDto;
import dev.aziz.movies.exceptions.AppException;
import dev.aziz.movies.exceptions.ObjectNotValidException;
import dev.aziz.movies.validators.ObjectsValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final ObjectsValidator<MovieDto> movieDtoObjectsValidator;
    public List<MovieDto> movieDtoList = new ArrayList<>(Arrays.asList(
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
        movieDtoObjectsValidator.validate(movieDto);
        MovieDto createdMovieDto = movieDto;
        movieDtoList.add(movieDto);
        return createdMovieDto;
    }

    public void deleteById(Long id) {
        MovieDto movieById = findMovieById(id);
        if (!movieDtoList.contains(movieById)) {
            throw new AppException("Movie not found.", HttpStatus.NOT_FOUND);
        }
        movieDtoList.remove(movieById);
    }

    public void updateById(Long id, UpdateDto updateDto) {
        MovieDto movieById = findMovieById(id);
        if (!movieDtoList.contains(movieById)) {
            throw new AppException("Movie not found.", HttpStatus.NOT_FOUND);
        }

        if ("".equals(updateDto.getDescription())){
            throw new ObjectNotValidException(Set.of("Description must not be empty."));
        }else if (updateDto.getDescription() != null) {
            movieById.setDescription(updateDto.getDescription());
        }

        if (updateDto.getRate() != 0) {
            if (updateDto.getRate() > 0 && updateDto.getRate() <= 5) {
                movieById.setRate(updateDto.getRate());
            } else if (updateDto.getRate() < 1 || updateDto.getRate() > 5) {
                throw new ObjectNotValidException(Set.of("Rate must not be empty and should be between 1 and 5."));
            }
        }

        if ("".equals(updateDto.getGenre())) {
            throw new ObjectNotValidException(Set.of("Genre must not be empty."));
        } else if (updateDto.getGenre() != null){
            movieById.setGenre(updateDto.getGenre());
        }
    }
}
