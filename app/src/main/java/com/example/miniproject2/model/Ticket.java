package com.example.miniproject2.model;

public class Ticket {
    private String seatNumber;
    private long price;  // per seat, in VND

    public Ticket(String seatNumber, long price) {
        this.seatNumber = seatNumber;
        this.price = price;
    }

    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }

    public long getPrice() { return price; }
    public void setPrice(long price) { this.price = price; }

    public String getPriceFormatted() {
        return String.format("%,d đ", price).replace(',', '.');
    }
}
