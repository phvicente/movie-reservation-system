package org.wogoo.moviereservationsystem.controller.dto;

import lombok.Data;

@Data
public class PopularMovieDto {
    private String title;
    private Long totalReservations;
}