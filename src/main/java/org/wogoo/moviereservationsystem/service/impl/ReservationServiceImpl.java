package org.wogoo.moviereservationsystem.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.wogoo.moviereservationsystem.controller.dto.ReservationSeatDto;
import org.wogoo.moviereservationsystem.domain.model.*;
import org.wogoo.moviereservationsystem.domain.repository.*;
import org.wogoo.moviereservationsystem.exception.BadRequestException;
import org.wogoo.moviereservationsystem.exception.ResourceNotFoundException;
import org.wogoo.moviereservationsystem.service.ReservationService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    private final SeatRepository seatRepository;

    private final ReservationSeatRepository reservationSeatRepository;

    private final ShowtimeRepository showtimeRepository;

    private final UserAccountRepository userAccountRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository, SeatRepository seatRepository, ReservationSeatRepository reservationSeatRepository, ShowtimeRepository showtimeRepository, UserAccountRepository userAccountRepository) {
        this.reservationRepository = reservationRepository;
        this.seatRepository = seatRepository;
        this.reservationSeatRepository = reservationSeatRepository;
        this.showtimeRepository = showtimeRepository;
        this.userAccountRepository = userAccountRepository;
    }


    @Override
    @Transactional
    public Reservation createReservation(Long userId, Long showtimeId) {

        Showtime showtime = showtimeRepository.findById(showtimeId)
                .orElseThrow(() -> new RuntimeException("Sessão não encontrada com ID: " + showtimeId));


        if (showtime.getEndTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Não é possível reservar para uma sessão encerrada.");
        }


        UserAccount user = userAccountRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + userId));


        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setShowtime(showtime);
        reservation.setReservationTime(LocalDateTime.now());

        return reservationRepository.save(reservation);
    }

    @Override
    @Transactional
    public void associateSeatsToReservation(Long userId, ReservationSeatDto reservationSeatDto) throws BadRequestException {

        Reservation reservation = reservationRepository.findById(reservationSeatDto.getReservationId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Reserva não encontrada com o ID: " + reservationSeatDto.getReservationId()));

        if (!reservation.getUser().getId().equals(userId)) {
            throw new BadRequestException("Você não tem permissão para modificar esta reserva.");
        }


        List<Seat> seats = seatRepository.findAllById(reservationSeatDto.getSeatIds());

        if (seats.size() != reservationSeatDto.getSeatIds().size()) {
            throw new BadRequestException("Um ou mais assentos não existem.");
        }

        for (Seat seat : seats) {
            if (seat.isReserved()) {
                throw new BadRequestException("O assento " + seat.getSeatNumber() + " já está reservado.");
            }
        }


        List<ReservationSeat> reservationSeats = new ArrayList<>();

        for (Seat seat : seats) {
            ReservationSeatId id = new ReservationSeatId();
            id.setReservationId(reservation.getId());
            id.setSeatId(seat.getId());

            ReservationSeat reservationSeat = new ReservationSeat();
            reservationSeat.setId(id);
            reservationSeat.setReservation(reservation);
            reservationSeat.setSeat(seat);

            reservationSeats.add(reservationSeat);

            seat.setReserved(true);
        }

        reservationSeatRepository.saveAll(reservationSeats);
        seatRepository.saveAll(seats);
    }

    @Override
    @Transactional
    public void cancelReservation(Long userId, Long reservationId) throws BadRequestException{

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva não encontrada com o ID: " + reservationId));


        if (!reservation.getUser().getId().equals(userId)) {
            throw new BadRequestException("Você não tem permissão para cancelar esta reserva.");
        }


        if (!reservation.isFutureShowtime()) {
            throw new BadRequestException("Não é possível cancelar reservas para sessões já ocorridas.");
        }


        List<ReservationSeat> reservationSeats = reservation.getReservationSeats();
        for (ReservationSeat rs : reservationSeats) {
            Seat seat = rs.getSeat();
            seat.setReserved(false);
            seatRepository.save(seat);
        }

        reservation.setCancelledAt(LocalDateTime.now());
        reservationRepository.save(reservation);
    }
}
