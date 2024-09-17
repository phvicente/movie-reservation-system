package org.wogoo.moviereservationsystem.domain.repository;

import org.springframework.data.repository.NoRepositoryBean;

import java.time.LocalDate;
import java.util.List;

@NoRepositoryBean
public interface ReservationRepositoryCustom {

    List<Object[]> getReservationActivityData(LocalDate startDate, LocalDate endDate);

    List<Object[]> getMostPopularMoviesData(LocalDate startDate, LocalDate endDate);
}
