package com.example.miniproject02.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.miniproject02.data.dao.MovieDao;
import com.example.miniproject02.data.dao.ShowtimeDao;
import com.example.miniproject02.data.dao.TheaterDao;
import com.example.miniproject02.data.dao.TicketDao;
import com.example.miniproject02.data.dao.UserDao;
import com.example.miniproject02.data.model.MovieEntity;
import com.example.miniproject02.data.model.ShowtimeEntity;
import com.example.miniproject02.data.model.TheaterEntity;
import com.example.miniproject02.data.model.TicketEntity;
import com.example.miniproject02.data.model.UserEntity;

@Database(
        entities = {
                UserEntity.class,
                MovieEntity.class,
                TheaterEntity.class,
                ShowtimeEntity.class,
                TicketEntity.class
        },
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract UserDao userDao();

    public abstract MovieDao movieDao();

    public abstract TheaterDao theaterDao();

    public abstract ShowtimeDao showtimeDao();

    public abstract TicketDao ticketDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "cinema.db")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    seed(db);
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static void seed(SupportSQLiteDatabase db) {
        db.execSQL("INSERT INTO Users (username, password, full_name) VALUES ('user1', '123456', 'Nguyen Van A')");
        db.execSQL("INSERT INTO Users (username, password, full_name) VALUES ('user2', '123456', 'Tran Thi B')");

        db.execSQL("INSERT INTO Movies (title, genre, duration_minutes, rating) VALUES ('Dune: Part Two', 'Sci-Fi', 166, '8.7')");
        db.execSQL("INSERT INTO Movies (title, genre, duration_minutes, rating) VALUES ('Inside Out 2', 'Animation', 96, '8.0')");
        db.execSQL("INSERT INTO Movies (title, genre, duration_minutes, rating) VALUES ('Godzilla x Kong', 'Action', 115, '7.2')");

        db.execSQL("INSERT INTO Theaters (name, address, city) VALUES ('CGV Vincom', '72 Le Thanh Ton, District 1', 'Ho Chi Minh City')");
        db.execSQL("INSERT INTO Theaters (name, address, city) VALUES ('Lotte Cinema', '469 Nguyen Huu Tho, District 7', 'Ho Chi Minh City')");
        db.execSQL("INSERT INTO Theaters (name, address, city) VALUES ('Beta Cinemas', '456 Dien Bien Phu, Binh Thanh', 'Ho Chi Minh City')");

        db.execSQL("INSERT INTO Showtimes (movie_id, theater_id, start_time, room, available_seats, price) VALUES (1, 1, '2026-03-28 09:00', 'Room 01', 40, 120000)");
        db.execSQL("INSERT INTO Showtimes (movie_id, theater_id, start_time, room, available_seats, price) VALUES (1, 2, '2026-03-28 19:30', 'Room 03', 55, 150000)");
        db.execSQL("INSERT INTO Showtimes (movie_id, theater_id, start_time, room, available_seats, price) VALUES (2, 3, '2026-03-28 14:15', 'Room 02', 70, 90000)");
        db.execSQL("INSERT INTO Showtimes (movie_id, theater_id, start_time, room, available_seats, price) VALUES (3, 1, '2026-03-29 21:00', 'IMAX', 30, 180000)");
        db.execSQL("INSERT INTO Showtimes (movie_id, theater_id, start_time, room, available_seats, price) VALUES (2, 2, '2026-03-29 10:45', 'Room 05', 65, 100000)");
    }
}
