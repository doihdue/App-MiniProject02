package com.example.miniproject02.data.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.miniproject02.data.model.TheaterEntity;

import java.util.List;

@Dao
public interface TheaterDao {
    @Query("SELECT * FROM Theaters ORDER BY name")
    List<TheaterEntity> getAll();
}
