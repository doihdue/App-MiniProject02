package com.example.miniproject02.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.miniproject02.data.model.UserEntity;

@Dao
public interface UserDao {
    @Query("SELECT * FROM Users WHERE username = :username AND password = :password LIMIT 1")
    UserEntity login(String username, String password);

    @Query("SELECT * FROM Users WHERE id = :id LIMIT 1")
    UserEntity findById(int id);

    @Insert
    long insert(UserEntity user);
}
