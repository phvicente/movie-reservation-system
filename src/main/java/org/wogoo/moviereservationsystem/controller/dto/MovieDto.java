package org.wogoo.moviereservationsystem.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MovieDto {

    @NotBlank(message = "O título é obrigatório")
    private String title;

    private String description;

    private String genre;

    private String posterImage;
}