package com.example.miniproject2.dal;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.miniproject2.entities.User;
import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    long insert(User user);

    @Query("SELECT * FROM users")
    List<User> getAll();

    @Query("SELECT * FROM users WHERE id = :id")
    User findById(int id);

    @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
    User login(String username, String password);
}
