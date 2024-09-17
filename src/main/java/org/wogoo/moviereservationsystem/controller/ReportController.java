package org.wogoo.moviereservationsystem.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wogoo.moviereservationsystem.controller.dto.PopularMovieDto;
import org.wogoo.moviereservationsystem.controller.dto.ReportRequestDto;
import org.wogoo.moviereservationsystem.controller.dto.ReservationActivityDto;
import org.wogoo.moviereservationsystem.service.ReportService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/reservation-activity")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReservationActivityDto>> getReservationActivityReport(
            @Valid @RequestBody ReportRequestDto reportRequestDto
    ) {
        List<ReservationActivityDto> report = reportService.generateReservationActivityReport(reportRequestDto);
        return ResponseEntity.ok(report);
    }

    @PostMapping("/popular-movies")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PopularMovieDto>> getMostPopularMoviesReport(
            @Valid @RequestBody ReportRequestDto reportRequestDto
    ) {
        List<PopularMovieDto> report = reportService.generateMostPopularMoviesReport(reportRequestDto);
        return ResponseEntity.ok(report);
    }


}
