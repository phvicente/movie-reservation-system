package org.wogoo.moviereservationsystem.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CancelReservationDto {
    @NotNull(message = "O ID da reserva é obrigatório")
    private Long reservationId;
}
