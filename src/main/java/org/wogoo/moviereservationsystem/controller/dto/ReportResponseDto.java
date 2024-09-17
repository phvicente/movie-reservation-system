package org.wogoo.moviereservationsystem.controller.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ReportResponseDto {

    private LocalDate date;
    private Long totalReservations;
    private Long totalSeatsReserved;
    private BigDecimal totalRevenue;
}