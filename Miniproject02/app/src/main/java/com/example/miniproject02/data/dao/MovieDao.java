package com.example.miniproject02.data.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.miniproject02.data.model.MovieEntity;

import java.util.List;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM Movies ORDER BY title")
    List<MovieEntity> getAll();
}
