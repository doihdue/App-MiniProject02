package com.example.miniproject02.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.miniproject02.data.model.TicketCard;
import com.example.miniproject02.data.model.TicketEntity;

import java.util.List;

@Dao
public interface TicketDao {
    @Insert
    long insert(TicketEntity ticket);

    @Query("SELECT seat_number FROM Tickets WHERE showtime_id = :showtimeId")
    List<String> getBookedSeatNumbers(int showtimeId);

    @Query("SELECT COUNT(*) FROM Tickets WHERE showtime_id = :showtimeId AND seat_number = :seatNumber")
    int countTicketByShowtimeAndSeat(int showtimeId, String seatNumber);

    @Query("SELECT tk.id AS ticketId, m.title AS movieTitle, th.name AS theaterName, s.start_time AS startTime, tk.seat_number AS seatNumber, s.price AS price, tk.booked_at AS bookedAt " +
            "FROM Tickets tk " +
            "INNER JOIN Showtimes s ON tk.showtime_id = s.id " +
            "INNER JOIN Movies m ON s.movie_id = m.id " +
            "INNER JOIN Theaters th ON s.theater_id = th.id " +
            "WHERE tk.user_id = :userId " +
            "ORDER BY tk.id DESC")
    List<TicketCard> getTicketsForUser(int userId);
}
