package com.example.miniproject2.dal;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.miniproject2.entities.Ticket;
import java.util.List;

@Dao
public interface TicketDAO {
    @Insert
    long insert(Ticket ticket);

    @Query("SELECT * FROM tickets")
    List<Ticket> getAll();

    @Query("SELECT * FROM tickets WHERE id = :id")
    Ticket findById(int id);
}
