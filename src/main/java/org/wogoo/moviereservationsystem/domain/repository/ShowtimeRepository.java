package org.wogoo.moviereservationsystem.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wogoo.moviereservationsystem.domain.model.Showtime;

public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {
}
