package org.wogoo.moviereservationsystem.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.wogoo.moviereservationsystem.controller.dto.SeatRequest;
import org.wogoo.moviereservationsystem.domain.model.Seat;
import org.wogoo.moviereservationsystem.service.impl.SeatServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/admin/showtimes/{showtimeId}/seats")
public class SeatController {

    private final SeatServiceImpl seatService;

    public SeatController(SeatServiceImpl seatService) {
        this.seatService = seatService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Seat>> addSeats(
            @PathVariable Long showtimeId,
            @Valid @RequestBody List<SeatRequest> seatRequest
    ) {
        List<Seat> seats = seatService.addSeats(showtimeId, seatRequest);
        return ResponseEntity.ok(seats);
    }
}