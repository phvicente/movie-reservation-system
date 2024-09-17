package org.wogoo.moviereservationsystem.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.wogoo.moviereservationsystem.controller.dto.MovieDto;
import org.wogoo.moviereservationsystem.domain.model.Movie;
import org.wogoo.moviereservationsystem.service.MovieService;

@RestController
@RequestMapping("/api/admin/movies")
public class MovieController {

    private final MovieService movieService;


    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addMovie(@Valid @RequestBody MovieDto movieDto) {
        Movie movie = movieService.addMovie(movieDto);
        return ResponseEntity.ok(movie);
    }

    @GetMapping
    public ResponseEntity<Page<Movie>> getMovies(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String genre,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sort
    ) {
        Page<Movie> movies = movieService.getMovies(title, genre, page, size, sort);
        return ResponseEntity.ok(movies);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Movie> updateMovie(
            @PathVariable Long id,
            @Valid @RequestBody MovieDto movieDto
    ) {
        Movie updatedMovie = movieService.updateMovie(id, movieDto);
        return ResponseEntity.ok(updatedMovie);
    }

}
