package dev.aziz.movies.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Data
@Entity
@Table(name = "movie")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Title should not be empty")
    private String title;

    @NotEmpty(message = "Description should not be empty")
    private String description;

    @NotNull
    @Min(value = 1850)
    @Max(value = 2050, message = "Produced year should not be empty and must be greater than 1850")
    private int producedYear;

    @NotNull
    @Min(value = 1)
    @Max(value = 5, message = "Rate should not be empty and must be between 1 and 5")
    private int rate;

    @NotEmpty(message = "Director should not be empty")
    private String director;

    @NotEmpty(message = "Main actors list should not be empty")
    private String mainActors;

    @NotEmpty(message = "Genre should not be empty")
    private String genre;

    @CreatedDate
    private Instant createdDate;

    @LastModifiedDate
    private Instant lastModifiedDate;
}
