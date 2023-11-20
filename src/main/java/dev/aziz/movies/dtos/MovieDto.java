package dev.aziz.movies.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieDto {

    private Long id;

    private String title;

    private String description;

    private int producedYear;

    private int rate;

    private String director;

    private String mainActors;

    private String genre;

    private Instant createdDate;

    private Instant lastModifiedDate;

}
