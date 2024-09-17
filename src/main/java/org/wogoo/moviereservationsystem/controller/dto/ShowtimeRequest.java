package org.wogoo.moviereservationsystem.controller.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShowtimeRequest {

    @NotNull(message = "O horário de início é obrigatório")
    private LocalDateTime startTime;

    @NotNull(message = "O horário de término é obrigatório")
    private LocalDateTime endTime;

    @NotNull(message = "O ID do filme é obrigatório")
    private Long movieId;


}
