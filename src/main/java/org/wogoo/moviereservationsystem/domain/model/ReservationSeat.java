package org.wogoo.moviereservationsystem.domain.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "reservation_seat",
        uniqueConstraints = @UniqueConstraint(columnNames = {"seat_id"}))
@Data
public class ReservationSeat {

    @EmbeddedId
    private ReservationSeatId id = new ReservationSeatId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("reservationId")
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId("seatId")
    @JoinColumn(name = "seat_id")
    private Seat seat;
}
