package org.wogoo.moviereservationsystem.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wogoo.moviereservationsystem.controller.dto.ShowtimeRequest;
import org.wogoo.moviereservationsystem.domain.model.Showtime;
import org.wogoo.moviereservationsystem.service.ShowtimeService;

@RestController
@RequestMapping("/api/admin/showtimes")
public class ShowtimeController {

    private final ShowtimeService showtimeService;

    public ShowtimeController(ShowtimeService showtimeService) {
        this.showtimeService = showtimeService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Showtime> addShowtime(@Valid @RequestBody ShowtimeRequest showtimeDto) {
        Showtime showtime = showtimeService.addShowtime(showtimeDto);
        return ResponseEntity.ok(showtime);
    }
}