package org.wogoo.moviereservationsystem.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wogoo.moviereservationsystem.domain.model.Reservation;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, ReservationRepositoryCustom {

    List<Reservation> findByUserId(Long userId);

}
