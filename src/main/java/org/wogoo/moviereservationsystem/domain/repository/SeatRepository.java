package org.wogoo.moviereservationsystem.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.wogoo.moviereservationsystem.domain.model.Seat;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    @Query("SELECT COUNT(s) > 0 FROM Seat s WHERE s.id IN :seatIds AND s.reserved = true")
    boolean existsAnyReservedSeat(@Param("seatIds") List<Long> seatIds);
}
