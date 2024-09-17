package org.wogoo.moviereservationsystem.service;

import org.wogoo.moviereservationsystem.controller.dto.ShowtimeRequest;
import org.wogoo.moviereservationsystem.domain.model.Showtime;

public interface ShowtimeService {
    Showtime addShowtime(ShowtimeRequest showtimeRequest);
}
