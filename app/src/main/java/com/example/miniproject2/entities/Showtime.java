package com.example.miniproject2.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "showtimes",
        foreignKeys = {
                @ForeignKey(entity = Movie.class,
                        parentColumns = "id",
                        childColumns = "movieId",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Theater.class,
                        parentColumns = "id",
                        childColumns = "theaterId",
                        onDelete = ForeignKey.CASCADE)
        })
public class Showtime {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int movieId;
    private int theaterId;
    private long showTime;
    private double price;

    public Showtime(int movieId, int theaterId, long showTime, double price) {
        this.movieId = movieId;
        this.theaterId = theaterId;
        this.showTime = showTime;
        this.price = price;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getMovieId() { return movieId; }
    public void setMovieId(int movieId) { this.movieId = movieId; }
    public int getTheaterId() { return theaterId; }
    public void setTheaterId(int theaterId) { this.theaterId = theaterId; }
    public long getShowTime() { return showTime; }
    public void setShowTime(long showTime) { this.showTime = showTime; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
