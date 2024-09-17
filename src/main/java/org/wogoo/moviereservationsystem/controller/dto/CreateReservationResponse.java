package org.wogoo.moviereservationsystem.controller.dto;

import lombok.Data;

@Data
public class CreateReservationResponse {
    private Long reservationId;
    private Long showtimeId;
}