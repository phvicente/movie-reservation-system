package org.wogoo.moviereservationsystem.service;

import org.wogoo.moviereservationsystem.controller.dto.SeatRequest;
import org.wogoo.moviereservationsystem.domain.model.Seat;

import java.util.List;

public interface SeatService {
    List<Seat> addSeats(Long showTimeId, List<SeatRequest> seatRequests);
}
