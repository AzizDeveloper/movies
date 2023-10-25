package dev.aziz.movies.services;

import dev.aziz.movies.dtos.MovieDto;
import dev.aziz.movies.dtos.ReducedMovieDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    List<MovieDto> movieDtoList = List.of(
            new MovieDto(1L, "Titanic", "A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.", 1997, 5, "James Cameron", List.of("Leonardo DiCaprio", "Kate Winslet", "Billy Zane", "Kathy Bates", "Frances Fisher"), "Drama"),
            new MovieDto(2L, "The Shawshank Redemption", "Over the course of several years, two convicts form a friendship, seeking consolation and, eventually, redemption through basic compassion.", 1994, 4, "Frank Darabont", List.of("Tim Robbins", "Morgan Freeman", "Bob Gunton", "William Sandler", "Clancy Brown"), "Drama"),
            new MovieDto(3L, "The Godfather", "Don Vito Corleone, head of a mafia family, decides to hand over his empire to his youngest son Michael. However, his decision unintentionally puts the lives of his loved ones in grave danger.", 1972, 3, "Francis Ford Coppola", List.of("Marlon Brando", "Al Pacino", "James Caan", "Richard S. Castellano", "Robert Duvall"), "Crime")
    );

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
                .orElseThrow();
        return foundMovie;
    }
}
