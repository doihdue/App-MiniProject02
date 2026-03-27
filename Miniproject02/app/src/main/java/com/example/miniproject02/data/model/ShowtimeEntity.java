package com.example.miniproject02.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "Showtimes",
        foreignKeys = {
                @ForeignKey(
                        entity = MovieEntity.class,
                        parentColumns = "id",
                        childColumns = "movie_id",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = TheaterEntity.class,
                        parentColumns = "id",
                        childColumns = "theater_id",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {@Index("movie_id"), @Index("theater_id")}
)
public class ShowtimeEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "movie_id")
    public int movieId;

    @ColumnInfo(name = "theater_id")
    public int theaterId;

    @ColumnInfo(name = "start_time")
    public String startTime;

    @ColumnInfo(name = "room")
    public String room;

    @ColumnInfo(name = "available_seats")
    public int availableSeats;

    @ColumnInfo(name = "price")
    public int price;
}
