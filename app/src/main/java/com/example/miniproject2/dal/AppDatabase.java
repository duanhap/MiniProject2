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
                userDAO.insert(new User("admin", "123456", "Administrator"));
                userDAO.insert(new User("user1", "123456", "Nguyen Van A"));

                // Seed Movies
                movieDAO.insert(new Movie("Avengers: Endgame", "Siêu anh hùng Marvel tái đấu Thanos", 181, "phim1"));
                movieDAO.insert(new Movie("Joker", "Câu chuyện về kẻ phản diện khét tiếng", 122, "phim2"));
                movieDAO.insert(new Movie("Interstellar", "Hành trình xuyên không gian tìm kiếm hy vọng", 169, "phim3"));
                movieDAO.insert(new Movie("Spider-Man: No Way Home", "Người nhện đối đầu đa vũ trụ", 148, "phim4"));

                // Seed Theaters
                theaterDAO.insert(new Theater("CGV Vincom Center", "72 Lê Thánh Tôn, Q.1, TP.HCM"));
                theaterDAO.insert(new Theater("Lotte Cinema Gò Vấp", "242 Nguyễn Văn Nghi, Gò Vấp"));

                // Seed Showtimes
                showtimeDAO.insert(new Showtime(1, 1, System.currentTimeMillis() + 3600000, 110000));   // Avengers - CGV - 1h later
                showtimeDAO.insert(new Showtime(1, 1, System.currentTimeMillis() + 10800000, 110000));  // Avengers - CGV - 3h later
                showtimeDAO.insert(new Showtime(1, 2, System.currentTimeMillis() + 7200000, 100000));   // Avengers - Lotte - 2h later
                showtimeDAO.insert(new Showtime(2, 1, System.currentTimeMillis() + 10800000, 120000));  // Joker - CGV - 3h later
                showtimeDAO.insert(new Showtime(2, 2, System.currentTimeMillis() + 14400000, 115000));  // Joker - Lotte - 4h later
                showtimeDAO.insert(new Showtime(3, 1, System.currentTimeMillis() + 14400000, 115000));  // Interstellar - CGV - 4h later
                showtimeDAO.insert(new Showtime(3, 2, System.currentTimeMillis() + 3600000, 110000));   // Interstellar - Lotte - 1h later
                showtimeDAO.insert(new Showtime(4, 1, System.currentTimeMillis() + 18000000, 130000));  // Spider-Man - CGV - 5h later
                showtimeDAO.insert(new Showtime(4, 2, System.currentTimeMillis() + 18000000, 125000));  // Spider-Man - Lotte - 5h later
                showtimeDAO.insert(new Showtime(1, 1, System.currentTimeMillis() + 25200000, 140000));  // Avengers - CGV - 7h later
            });
        }
    };
}
