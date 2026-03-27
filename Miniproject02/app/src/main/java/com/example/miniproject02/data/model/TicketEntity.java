package com.example.miniproject02.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "Tickets",
        foreignKeys = {
                @ForeignKey(
                        entity = UserEntity.class,
                        parentColumns = "id",
                        childColumns = "user_id",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = ShowtimeEntity.class,
                        parentColumns = "id",
                        childColumns = "showtime_id",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {@Index("user_id"), @Index("showtime_id")}
)
public class TicketEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "user_id")
    public int userId;

    @ColumnInfo(name = "showtime_id")
    public int showtimeId;

    @ColumnInfo(name = "seat_number")
    public String seatNumber;

    @ColumnInfo(name = "booked_at")
    public String bookedAt;
}
