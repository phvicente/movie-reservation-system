package org.wogoo.moviereservationsystem.service;

import org.springframework.data.domain.Page;
import org.wogoo.moviereservationsystem.controller.dto.MovieDto;
import org.wogoo.moviereservationsystem.domain.model.Movie;

public interface MovieService {

    Movie addMovie(MovieDto movieDto);

    Page<Movie> getMovies(String title, String genre, int page, int size, String sort);

    Movie updateMovie(Long id, MovieDto movieDto);
}
