package com.example.miniproject2.dal;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.miniproject2.entities.Movie;
import java.util.List;

@Dao
public interface MovieDAO {
    @Insert
    long insert(Movie movie);

    @Query("SELECT * FROM movies")
    List<Movie> getAll();

    @Query("SELECT * FROM movies WHERE id = :id")
    Movie findById(int id);
}
