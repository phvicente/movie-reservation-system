package org.wogoo.moviereservationsystem.domain.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class ReservationSeatId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "reservation_id")
    private Long reservationId;

    @Column(name = "seat_id")
    private Long seatId;
}