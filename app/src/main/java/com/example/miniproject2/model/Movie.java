package com.example.miniproject2.model;

public class Movie {
    private String title;
    private int imageResId;  // drawable resource ID
    private int duration;    // in minutes

    public Movie(String title, int imageResId, int duration) {
        this.title = title;
        this.imageResId = imageResId;
        this.duration = duration;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getImageResId() { return imageResId; }
    public void setImageResId(int imageResId) { this.imageResId = imageResId; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public String getDurationFormatted() {
        return duration + " phút";
    }
}
