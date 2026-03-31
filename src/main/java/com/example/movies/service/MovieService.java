package com.example.movies.service;

import com.example.movies.dto.MovieDTO;
import com.example.movies.model.*;
import com.example.movies.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;
    private final GenreRepository genreRepository;
    private final DirectorRepository directorRepository;

    // ─── Mapping ────────────────────────────────────────────────────────────────

    public MovieDTO toDTO(Movie movie) {
        return MovieDTO.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .year(movie.getYear())
                .overview(movie.getOverview())
                .posterUrl(movie.getPosterUrl())
                .director(movie.getDirector() != null ? movie.getDirector().getName() : null)
                .genres(movie.getGenres() == null ? Set.of() :
                        movie.getGenres().stream().map(Genre::getName).collect(Collectors.toSet()))
                .actors(movie.getActors() == null ? Set.of() :
                        movie.getActors().stream().map(Actor::getName).collect(Collectors.toSet()))
                .build();
    }

    private Movie fromDTO(MovieDTO dto) {
        Movie movie = new Movie();
        movie.setTitle(dto.getTitle());
        movie.setYear(dto.getYear());
        movie.setOverview(dto.getOverview());
        movie.setPosterUrl(dto.getPosterUrl());

        if (dto.getDirector() != null && !dto.getDirector().isBlank()) {
            Director director = directorRepository.findByName(dto.getDirector())
                    .orElseGet(() -> directorRepository.save(
                            Director.builder().name(dto.getDirector()).build()));
            movie.setDirector(director);
        }

        if (dto.getActors() != null) {
            Set<Actor> actors = dto.getActors().stream()
                    .filter(name -> name != null && !name.isBlank())
                    .map(name -> actorRepository.findByName(name)
                            .orElseGet(() -> actorRepository.save(
                                    Actor.builder().name(name).build())))
                    .collect(Collectors.toSet());
            movie.setActors(actors);
        }

        if (dto.getGenres() != null) {
            Set<Genre> genres = dto.getGenres().stream()
                    .filter(name -> name != null && !name.isBlank())
                    .map(name -> genreRepository.findByName(name)
                            .orElseGet(() -> genreRepository.save(
                                    Genre.builder().name(name).build())))
                    .collect(Collectors.toSet());
            movie.setGenres(genres);
        }

        return movie;
    }

    // ─── CRUD ───────────────────────────────────────────────────────────────────

    public List<MovieDTO> findAll() {
        return movieRepository.findAll().stream().map(this::toDTO).toList();
    }

    public Optional<MovieDTO> findById(Long id) {
        return movieRepository.findById(id).map(this::toDTO);
    }

    public MovieDTO create(MovieDTO dto) {
        Movie saved = movieRepository.save(fromDTO(dto));
        return toDTO(saved);
    }

    public Optional<MovieDTO> update(Long id, MovieDTO dto) {
        return movieRepository.findById(id).map(existing -> {
            Movie updated = fromDTO(dto);
            updated.setId(existing.getId());
            return toDTO(movieRepository.save(updated));
        });
    }

    public boolean delete(Long id) {
        if (movieRepository.existsById(id)) {
            movieRepository.deleteById(id);
            return true;
        }
        return false;
    }
}