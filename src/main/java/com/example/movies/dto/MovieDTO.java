package com.example.movies.dto;

import lombok.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieDTO {
    private Long id;
    private String title;
    private String year;
    private String overview;
    private String posterUrl;
    private String director;
    private Set<String> genres;
    private Set<String> actors;
}