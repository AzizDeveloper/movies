package dev.aziz.movies.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieDto {

    @NotNull
    private Long id;

    @NotEmpty(message = "Title should not be empty")
    private String title;

    @NotEmpty(message = "Description should not be empty")
    private String description;

    @NotNull
    @Min(value = 1850)
    @Max(value = 2050, message = "Year should not be empty and must be greater than 1850")
    private int year;

    @NotNull
    @Min(value = 1)
    @Max(value = 5, message = "Rate should not be empty and must be between 1 and 5")
    private int rate;

    @NotEmpty(message = "Director should not be empty")
    private String director;

    @NotEmpty(message = "Main actors list should not be empty")
    private List<String> mainActors;

    @NotEmpty(message = "Genre should not be empty")
    private String genre;

}
