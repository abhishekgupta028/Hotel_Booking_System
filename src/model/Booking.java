package model;

import java.time.LocalDate;

public class Booking {
    private int id;
    private int roomId;
    private String customerName;
    private String contact;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private double totalCost;

    // This field is used for displaying room number in the UI
    private String roomNumber;

    public Booking(int id, int roomId, String customerName, String contact,
                   LocalDate checkInDate, LocalDate checkOutDate, double totalCost) {
        this.id = id;
        this.roomId = roomId;
        this.customerName = customerName;
        this.contact = contact;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalCost = totalCost;
    }

    public int getId() {
        return id;
    }

    public int getRoomId() {
        return roomId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getContact() {
        return contact;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber + " booked by " + customerName +
                " from " + checkInDate + " to " + checkOutDate;
    }
}
