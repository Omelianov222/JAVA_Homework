package com.example.movies.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DirectorDTO {
    private Long id;
    private String name;
    private String bio;
}