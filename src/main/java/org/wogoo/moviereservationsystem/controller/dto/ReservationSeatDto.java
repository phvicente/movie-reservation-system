package org.wogoo.moviereservationsystem.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ReservationSeatDto {

    @NotNull(message = "O ID da reserva é obrigatório")
    private Long reservationId;

    @NotEmpty(message = "A lista de IDs de assentos não pode estar vazia")
    private List<Long> seatIds;
}