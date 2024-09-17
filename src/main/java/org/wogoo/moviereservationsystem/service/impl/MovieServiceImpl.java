package org.wogoo.moviereservationsystem.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.wogoo.moviereservationsystem.controller.dto.MovieDto;
import org.wogoo.moviereservationsystem.domain.model.Movie;
import org.wogoo.moviereservationsystem.domain.repository.MovieRepository;
import org.wogoo.moviereservationsystem.exception.ResourceNotFoundException;
import org.wogoo.moviereservationsystem.service.MovieService;
import org.wogoo.moviereservationsystem.specification.MovieSpecification;


@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Movie addMovie(MovieDto movieDto) {
        Movie movie = new Movie();
        movie.setTitle(movieDto.getTitle());
        movie.setDescription(movieDto.getDescription());
        movie.setGenre(movieDto.getGenre());
        movie.setPosterImage(movieDto.getPosterImage());

        return movieRepository.save(movie);
    }

    @Override
    public Page<Movie> getMovies(String title,
                                 String genre,
                                 int page,
                                 int size,
                                 String sort) {

        Specification<Movie> specs = Specification.where(null);

        if (title != null && !title.isEmpty()) {
            specs = specs.and(MovieSpecification.titleContains(title));
        }

        if (genre != null && !genre.isEmpty()) {
            specs = specs.and(MovieSpecification.genreEquals(genre));
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

        return movieRepository.findAll(specs, pageable);

    }

    @Override

    public Movie updateMovie(Long id, MovieDto movieDto) {
        Movie existingMovie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Filme não encontrado com o ID: " + id));

        existingMovie.setTitle(movieDto.getTitle());
        existingMovie.setDescription(movieDto.getDescription());
        existingMovie.setGenre(movieDto.getGenre());
        existingMovie.setPosterImage(movieDto.getPosterImage());

        return movieRepository.save(existingMovie);
    }
}
