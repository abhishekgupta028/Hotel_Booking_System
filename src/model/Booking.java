package model;

import java.time.LocalDate;

public class Booking {
    private int id;
    private int roomId;
    private String customerName;
    private String contact;            // ✅ New field
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private double totalCost;         // ✅ New field

    // ✅ Updated constructor with new fields
    public Booking(int id, int roomId, String customerName, String contact, LocalDate checkInDate, LocalDate checkOutDate, double totalCost) {
        this.id = id;
        this.roomId = roomId;
        this.customerName = customerName;
        this.contact = contact;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalCost = totalCost;
    }

    // ✅ Overloaded constructor (without id, for inserting new bookings)
    public Booking(int roomId, String customerName, String contact, LocalDate checkInDate, LocalDate checkOutDate, double totalCost) {
        this.roomId = roomId;
        this.customerName = customerName;
        this.contact = contact;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalCost = totalCost;
    }

    // ✅ Getters and setters
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

    public void setId(int id) {
        this.id = id;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
