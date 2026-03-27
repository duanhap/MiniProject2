package com.example.miniproject2.dal;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.miniproject2.entities.Theater;
import java.util.List;

@Dao
public interface TheaterDAO {
    @Insert
    long insert(Theater theater);

    @Query("SELECT * FROM theaters")
    List<Theater> getAll();

    @Query("SELECT * FROM theaters WHERE id = :id")
    Theater findById(int id);
}
