package com.example.miniproject2.dal;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.miniproject2.entities.Showtime;
import java.util.List;

@Dao
public interface ShowtimeDAO {
    @Insert
    long insert(Showtime showtime);

    @Query("SELECT * FROM showtimes")
    List<Showtime> getAll();

    @Query("SELECT * FROM showtimes WHERE id = :id")
    Showtime findById(int id);
}
