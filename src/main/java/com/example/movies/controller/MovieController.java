package com.example.movies.controller;

import com.example.movies.dto.MovieDTO;
import com.example.movies.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    // READ ALL
    @GetMapping
    public List<MovieDTO> all() {
        return movieService.findAll();
    }

    // READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> get(@PathVariable Long id) {
        return movieService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE
    @PostMapping
    public ResponseEntity<MovieDTO> create(@RequestBody MovieDTO dto) {
        return ResponseEntity.ok(movieService.create(dto));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> update(@PathVariable Long id, @RequestBody MovieDTO dto) {
        return movieService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return movieService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}