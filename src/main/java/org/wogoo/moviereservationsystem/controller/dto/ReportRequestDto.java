package org.wogoo.moviereservationsystem.controller.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class ReportRequestDto {

    @NotNull(message = "A data inicial é obrigatória")
    private LocalDate startDate;

    @NotNull(message = "A data final é obrigatória")
    private LocalDate endDate;
}
