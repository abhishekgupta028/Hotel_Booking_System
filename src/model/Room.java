package model;

public class Room {
    private int id;
    private String roomNumber;
    private String type;
    private String status;
    private double price;

    public Room(int id, String roomNumber, String type, String status, double price) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.type = type;
        this.status = status;
        this.price = price;
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // To display room number in JComboBox nicely
    @Override
    public String toString() {
        return roomNumber + " (" + type + ")";
    }
}
