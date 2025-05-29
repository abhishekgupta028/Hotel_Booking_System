package model;

public class Room {
    private int id;
    private int number;
    private String type;
    private double price;
    private String status;

    public Room(int id, int number, String type, double price, String status) {
        this.id = id;
        this.number = number;
        this.type = type;
        this.price = price;
        this.status = status;
    }

    public Room() {
        // No-arg constructor added
    }

    public int getId() { return id; }
    public int getRoomNumber() { return number; }
    public String getType() { return type; }
    public double getPrice() { return price; }
    public String getStatus() { return status; }

    public void setId(int id) { this.id = id; }
    public void setRoomNumber(int number) { this.number = number; }
    public void setType(String type) { this.type = type; }
    public void setPrice(double price) { this.price = price; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("Room %d (%s) - ₹%.2f [%s]", number, type, price, status);
    }
}
