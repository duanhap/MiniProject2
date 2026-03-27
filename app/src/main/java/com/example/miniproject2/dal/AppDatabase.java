package com.example.miniproject2.dal;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.example.miniproject2.entities.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Movie.class, Theater.class, Showtime.class, Ticket.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase instance;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract UserDAO userDAO();
    public abstract MovieDAO movieDAO();
    public abstract TheaterDAO theaterDAO();
    public abstract ShowtimeDAO showtimeDAO();
    public abstract TicketDAO ticketDAO();

    public static AppDatabase getInstance(final Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "movie_booking_db")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return instance;
    }

    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                UserDAO userDAO = instance.userDAO();
                MovieDAO movieDAO = instance.movieDAO();
                TheaterDAO theaterDAO = instance.theaterDAO();
                ShowtimeDAO showtimeDAO = instance.showtimeDAO();

                // Seed Users
                userDAO.insert(new User("admin", "123", "Administrator"));
                userDAO.insert(new User("user1", "123", "Nguyen Van A"));

                // Seed Movies
                movieDAO.insert(new Movie("Avengers: Endgame", "Marvel movie", 180, "movie"));
                movieDAO.insert(new Movie("Joker", "DC movie", 122, "movie1"));
                movieDAO.insert(new Movie("Interstellar", "Sci-fi movie", 169, "movie2"));

                // Seed Theaters
                theaterDAO.insert(new Theater("CGV Vincom", "District 1, HCM"));
                theaterDAO.insert(new Theater("Lotte Cinema", "District 7, HCM"));

                // Seed Showtimes (Assuming IDs start from 1)
                showtimeDAO.insert(new Showtime(1, 1, System.currentTimeMillis() + 3600000, 100000));
                showtimeDAO.insert(new Showtime(1, 2, System.currentTimeMillis() + 7200000, 95000));
                showtimeDAO.insert(new Showtime(2, 1, System.currentTimeMillis() + 10800000, 120000));
                showtimeDAO.insert(new Showtime(3, 1, System.currentTimeMillis() + 14400000, 110000));
                showtimeDAO.insert(new Showtime(3, 2, System.currentTimeMillis() + 18000000, 105000));
            });
        }
    };
}
