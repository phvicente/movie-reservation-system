package org.wogoo.moviereservationsystem.domain.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ReservationRepositoryImpl implements ReservationRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Object[]> getReservationActivityData(LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT DATE(r.reservation_time) AS date, " +
                "COUNT(DISTINCT r.id) AS totalReservations, " +
                "COUNT(rs.seat_id) AS totalSeatsReserved " +
                "FROM reservation r " +
                "JOIN reservation_seat rs ON r.id = rs.reservation_id " +
                "WHERE DATE(r.reservation_time) BETWEEN :startDate AND :endDate " +
                "GROUP BY DATE(r.reservation_time) " +
                "ORDER BY DATE(r.reservation_time)";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("startDate", java.sql.Date.valueOf(startDate));
        query.setParameter("endDate", java.sql.Date.valueOf(endDate));

        @SuppressWarnings("unchecked")
        List<Object[]> results = query.getResultList();

        return results;
    }

    @Override
    public List<Object[]> getMostPopularMoviesData(LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT m.title, " +
                "COUNT(DISTINCT r.id) AS totalReservations " +
                "FROM reservation r " +
                "JOIN showtime s ON r.showtime_id = s.id " +
                "JOIN movie m ON s.movie_id = m.id " +
                "WHERE DATE(r.reservation_time) BETWEEN :startDate AND :endDate " +
                "GROUP BY m.title " +
                "ORDER BY totalReservations DESC " +
                "LIMIT 5";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("startDate", java.sql.Date.valueOf(startDate));
        query.setParameter("endDate", java.sql.Date.valueOf(endDate));

        @SuppressWarnings("unchecked")
        List<Object[]> results = query.getResultList();

        return results;
    }
}
