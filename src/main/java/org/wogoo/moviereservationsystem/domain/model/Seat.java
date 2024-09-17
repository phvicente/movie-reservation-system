package org.wogoo.moviereservationsystem.domain.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "seat",
        uniqueConstraints = @UniqueConstraint(columnNames = {"seat_number", "showtime_id"}))
@Data
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "seat_number", nullable = false, length = 10)
    private String seatNumber;

    @Column(name = "reserved", nullable = false)
    private boolean reserved = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "showtime_id", nullable = false)
    private Showtime showtime;

    @OneToOne(mappedBy = "seat")
    private ReservationSeat reservationSeat;
}
