package org.wogoo.moviereservationsystem.service;

import org.wogoo.moviereservationsystem.controller.dto.ReservationSeatDto;
import org.wogoo.moviereservationsystem.domain.model.Reservation;

public interface ReservationService {

    Reservation createReservation(Long userId, Long showtimeId);
    void associateSeatsToReservation(Long userId, ReservationSeatDto reservationSeatDto);
    void cancelReservation(Long userId, Long reservationId);

}
