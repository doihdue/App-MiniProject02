package com.example.miniproject02.data.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Update;

import com.example.miniproject02.data.model.ShowtimeCard;
import com.example.miniproject02.data.model.ShowtimeEntity;

import java.util.List;

@Dao
public interface ShowtimeDao {
    @Query("SELECT s.id AS showtimeId, m.title AS movieTitle, t.name AS theaterName, s.start_time AS startTime, s.room AS room, s.price AS price, s.available_seats AS availableSeats " +
            "FROM Showtimes s " +
            "INNER JOIN Movies m ON s.movie_id = m.id " +
            "INNER JOIN Theaters t ON s.theater_id = t.id " +
            "WHERE s.id = :showtimeId")
        ShowtimeCard getShowtimeCardById(int showtimeId);

        @Query("SELECT s.id AS showtimeId, m.title AS movieTitle, t.name AS theaterName, s.start_time AS startTime, s.room AS room, s.price AS price, s.available_seats AS availableSeats " +
            "FROM Showtimes s " +
            "INNER JOIN Movies m ON s.movie_id = m.id " +
            "INNER JOIN Theaters t ON s.theater_id = t.id " +
            "ORDER BY s.start_time")
    List<ShowtimeCard> getAllShowtimeCards();

    @Query("SELECT * FROM Showtimes WHERE id = :showtimeId LIMIT 1")
    ShowtimeEntity findById(int showtimeId);

    @Update
    int update(ShowtimeEntity entity);
}
