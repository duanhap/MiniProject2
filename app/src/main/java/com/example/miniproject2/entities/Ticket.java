package com.example.miniproject2.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "tickets",
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "id",
                        childColumns = "userId",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Showtime.class,
                        parentColumns = "id",
                        childColumns = "showtimeId",
                        onDelete = ForeignKey.CASCADE)
        })
public class Ticket {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int userId;
    private int showtimeId;
    private String seatNumber;
    private double price;

    public Ticket(int userId, int showtimeId, String seatNumber, double price) {
        this.userId = userId;
        this.showtimeId = showtimeId;
        this.seatNumber = seatNumber;
        this.price = price;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getShowtimeId() { return showtimeId; }
    public void setShowtimeId(int showtimeId) { this.showtimeId = showtimeId; }
    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
