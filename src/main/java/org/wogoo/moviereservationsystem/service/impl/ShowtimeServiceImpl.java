package org.wogoo.moviereservationsystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wogoo.moviereservationsystem.controller.dto.ShowtimeRequest;
import org.wogoo.moviereservationsystem.domain.model.Movie;
import org.wogoo.moviereservationsystem.domain.model.Showtime;
import org.wogoo.moviereservationsystem.domain.repository.MovieRepository;
import org.wogoo.moviereservationsystem.domain.repository.ShowtimeRepository;
import org.wogoo.moviereservationsystem.exception.ResourceNotFoundException;
import org.wogoo.moviereservationsystem.service.ShowtimeService;

@Service
public class ShowtimeServiceImpl implements ShowtimeService {

    private final ShowtimeRepository showtimeRepository;

    private final MovieRepository movieRepository;

    public ShowtimeServiceImpl(ShowtimeRepository showtimeRepository, MovieRepository movieRepository) {
        this.showtimeRepository = showtimeRepository;
        this.movieRepository = movieRepository;
    }


    @Override
    public Showtime addShowtime(ShowtimeRequest showtimeRequest) {
        Movie movie = movieRepository.findById(showtimeRequest.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Filme não encontrado com o ID: " + showtimeRequest.getMovieId()));

        Showtime showtime = new Showtime();
        showtime.setStartTime(showtimeRequest.getStartTime());
        showtime.setEndTime(showtimeRequest.getEndTime());
        showtime.setMovie(movie);

        return showtimeRepository.save(showtime);
    }
}
