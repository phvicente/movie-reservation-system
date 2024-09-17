package org.wogoo.moviereservationsystem.controller.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ReservationActivityDto {
    private LocalDate date;
    private Long totalReservations;
    private Long totalSeatsReserved;
}
