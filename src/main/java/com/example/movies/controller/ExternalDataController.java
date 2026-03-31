package com.example.movies.controller;

import com.example.movies.dto.MovieDTO;
import com.example.movies.service.ExternalDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ExternalDataController {

    private final ExternalDataService externalDataService;

    @GetMapping("/api/fetch/api")
    public String fetchFromApi() {
        try {
            externalDataService.fetchMoviesFromApi();
            return "Fetched movies from API successfully";
        } catch (IOException e) {
            return "Error: " + e.getMessage();
        }
    }

    @GetMapping("/api/fetch/html")
    public String fetchFromHtml(
            @RequestParam String url,
            @RequestParam(required = false) String container,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String overview,
            @RequestParam(required = false) String director,
            @RequestParam(required = false) String poster,
            @RequestParam(required = false) String year
    ) {
        try {
            int count = externalDataService.fetchMoviesFromHtml(
                    url, container, title, overview, director, poster, year);
            return "Збережено " + count + " фільм(ів) з HTML-сторінки";
        } catch (IOException e) {
            return "Error: " + e.getMessage();
        }
    }

    /**
     * Попередній перегляд — парсить HTML але НЕ зберігає до БД.
     * Повертає JSON-список знайдених фільмів.
     */
    @GetMapping("/api/fetch/html/preview")
    public List<MovieDTO> previewFromHtml(
            @RequestParam String url,
            @RequestParam(required = false) String container,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String overview,
            @RequestParam(required = false) String director,
            @RequestParam(required = false) String poster,
            @RequestParam(required = false) String year
    ) {
        try {
            return externalDataService.previewMoviesFromHtml(
                    url, container, title, overview, director, poster, year);
        } catch (IOException e) {
            return List.of();
        }
    }
}