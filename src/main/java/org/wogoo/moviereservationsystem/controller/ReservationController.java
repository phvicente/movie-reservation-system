package org.wogoo.moviereservationsystem.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wogoo.moviereservationsystem.controller.dto.CancelReservationDto;
import org.wogoo.moviereservationsystem.controller.dto.CreateReservationRequest;
import org.wogoo.moviereservationsystem.controller.dto.CreateReservationResponse;
import org.wogoo.moviereservationsystem.controller.dto.ReservationSeatDto;
import org.wogoo.moviereservationsystem.domain.model.Reservation;
import org.wogoo.moviereservationsystem.security.JwtTokenUtil;
import org.wogoo.moviereservationsystem.service.ReservationService;


@RestController
@RequestMapping("/api/user/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping
    public ResponseEntity<CreateReservationResponse> createReservation(
            @Valid @RequestBody CreateReservationRequest createReservationRequest,
            HttpServletRequest request
    ) {
        String token = jwtTokenUtil.getTokenFromRequest(request);
        if (token == null || !jwtTokenUtil.validateToken(token)) {
            return ResponseEntity.status(401).body(null);
        }

        Long userId = jwtTokenUtil.getUserIdFromToken(token);


        Reservation reservation = reservationService.createReservation(userId, createReservationRequest.getShowtimeId());

        CreateReservationResponse response = new CreateReservationResponse();
        response.setReservationId(reservation.getId());
        response.setShowtimeId(reservation.getShowtime().getId());


        return ResponseEntity.ok(response);
    }



    @PostMapping("/seats")
    public ResponseEntity<String> associateSeats(
            @Valid @RequestBody ReservationSeatDto reservationSeatDto,
            HttpServletRequest request
    ) throws BadRequestException {
        String token = jwtTokenUtil.getTokenFromRequest(request);
        if (token == null || !jwtTokenUtil.validateToken(token)) {
            return ResponseEntity.status(401).body("Token JWT inválido ou ausente");
        }


        Long userId = jwtTokenUtil.getUserIdFromToken(token);


        reservationService.associateSeatsToReservation(userId, reservationSeatDto);

        return ResponseEntity.ok("Assentos associados com sucesso à reserva.");
    }

    @PostMapping("/cancel")
    public ResponseEntity<String> cancelReservation(
            @Valid @RequestBody CancelReservationDto cancelReservationDto,
            HttpServletRequest request
    ) throws BadRequestException {

        String token = jwtTokenUtil.getTokenFromRequest(request);
        if (token == null || !jwtTokenUtil.validateToken(token)) {
            return ResponseEntity.status(401).body("Token JWT inválido ou ausente");
        }

        Long userId = jwtTokenUtil.getUserIdFromToken(token);

        reservationService.cancelReservation(userId, cancelReservationDto.getReservationId());

        return ResponseEntity.ok("Reserva cancelada com sucesso.");
    }
}