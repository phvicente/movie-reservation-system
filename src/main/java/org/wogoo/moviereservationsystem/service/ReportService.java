package org.wogoo.moviereservationsystem.service;

import org.wogoo.moviereservationsystem.controller.dto.PopularMovieDto;
import org.wogoo.moviereservationsystem.controller.dto.ReportRequestDto;
import org.wogoo.moviereservationsystem.controller.dto.ReservationActivityDto;

import java.util.List;

public interface ReportService {

    List<ReservationActivityDto> generateReservationActivityReport(ReportRequestDto reportRequestDto);
    List<PopularMovieDto> generateMostPopularMoviesReport(ReportRequestDto reportRequestDto);

}
