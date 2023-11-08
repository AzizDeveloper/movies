package dev.aziz.movies.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateMovieDto {

    @NotEmpty(message = "Description should not be empty")
    private String description;

    @NotNull
    @Min(value = 1, message = "Rate should not be empty and must be between 1 and 5")
    @Max(value = 5, message = "Rate should not be empty and must be between 1 and 5")
    private int rate;

    @NotEmpty(message = "Genre should not be empty")
    private String genre;

}
