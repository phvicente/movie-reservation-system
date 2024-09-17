package org.wogoo.moviereservationsystem.service.impl;

import org.springframework.stereotype.Service;
import org.wogoo.moviereservationsystem.controller.dto.PopularMovieDto;
import org.wogoo.moviereservationsystem.controller.dto.ReportRequestDto;
import org.wogoo.moviereservationsystem.controller.dto.ReservationActivityDto;
import org.wogoo.moviereservationsystem.domain.repository.ReservationRepository;
import org.wogoo.moviereservationsystem.service.ReportService;

import java.util.List;
import java.sql.Date;
import java.util.ArrayList;


@Service
public class ReportServiceImpl implements ReportService {

    private final ReservationRepository reservationRepository;

    public ReportServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }


    @Override
    public List<ReservationActivityDto> generateReservationActivityReport(ReportRequestDto reportRequestDto) {

        List<Object[]> results = reservationRepository.getReservationActivityData(
                reportRequestDto.getStartDate(),
                reportRequestDto.getEndDate()
        );

        List<ReservationActivityDto> report = new ArrayList<>();

        for (Object[] row : results) {
            ReservationActivityDto dto = new ReservationActivityDto();
            dto.setDate(((Date) row[0]).toLocalDate());
            dto.setTotalReservations(((Number) row[1]).longValue());
            dto.setTotalSeatsReserved(((Number) row[2]).longValue());
            report.add(dto);
        }

        return report;
    }

    @Override
    public List<PopularMovieDto> generateMostPopularMoviesReport(ReportRequestDto reportRequestDto) {

        List<Object[]> results = reservationRepository.getMostPopularMoviesData(
                reportRequestDto.getStartDate(),
                reportRequestDto.getEndDate()
        );

        List<PopularMovieDto> report = new ArrayList<>();

        for (Object[] row : results) {
            PopularMovieDto dto = new PopularMovieDto();
            dto.setTitle((String) row[0]);
            dto.setTotalReservations(((Number) row[1]).longValue());
            report.add(dto);
        }

        return report;
    }
}
