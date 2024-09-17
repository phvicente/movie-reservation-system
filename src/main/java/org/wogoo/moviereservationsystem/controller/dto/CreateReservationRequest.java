package org.wogoo.moviereservationsystem.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class CreateReservationRequest {

    @NotNull(message = "showtimeId é obrigatório.")
    private Long showtimeId;

}
