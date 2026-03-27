package com.example.miniproject02.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Movies")
public class MovieEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "genre")
    public String genre;

    @ColumnInfo(name = "duration_minutes")
    public int durationMinutes;

    @ColumnInfo(name = "rating")
    public String rating;
}
