package org.wogoo.moviereservationsystem.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wogoo.moviereservationsystem.domain.model.ReservationSeat;

public interface ReservationSeatRepository extends JpaRepository<ReservationSeat, Long> {
}
