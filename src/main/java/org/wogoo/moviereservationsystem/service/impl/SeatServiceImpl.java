package org.wogoo.moviereservationsystem.service.impl;

import org.springframework.stereotype.Service;
import org.wogoo.moviereservationsystem.controller.dto.SeatRequest;
import org.wogoo.moviereservationsystem.domain.model.Seat;
import org.wogoo.moviereservationsystem.domain.model.Showtime;
import org.wogoo.moviereservationsystem.domain.repository.SeatRepository;
import org.wogoo.moviereservationsystem.domain.repository.ShowtimeRepository;
import org.wogoo.moviereservationsystem.exception.ResourceNotFoundException;
import org.wogoo.moviereservationsystem.service.SeatService;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;

    private final ShowtimeRepository showtimeRepository;

    public SeatServiceImpl(SeatRepository seatRepository, ShowtimeRepository showtimeRepository) {
        this.seatRepository = seatRepository;
        this.showtimeRepository = showtimeRepository;
    }

    @Override
    public List<Seat> addSeats(Long showtimeId, List<SeatRequest> seatRequests) {

        Showtime showtime = showtimeRepository.findById(showtimeId)
                .orElseThrow(() -> new ResourceNotFoundException("Showtime não encontrado com o ID: " + showtimeId));

        List<Seat> seats = new ArrayList<>();

        for (SeatRequest seatRequest : seatRequests) {
            Seat seat = new Seat();
            seat.setSeatNumber(seatRequest.getSeatNumber());
            seat.setShowtime(showtime);
            seats.add(seat);
        }

        return seatRepository.saveAll(seats);
    }
}
