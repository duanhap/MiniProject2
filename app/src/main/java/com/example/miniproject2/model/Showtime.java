package com.example.miniproject2.model;

public class Showtime {
    private String time;
    private long price;  // in VND

    public Showtime(String time, long price) {
        this.time = time;
        this.price = price;
    }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public long getPrice() { return price; }
    public void setPrice(long price) { this.price = price; }

    public String getPriceFormatted() {
        return String.format("%,d đ", price).replace(',', '.');
    }
}
