package org.wogoo.moviereservationsystem.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SeatRequest {

    @NotBlank(message = "O número do assento é obrigatório")
    private String seatNumber;
}
