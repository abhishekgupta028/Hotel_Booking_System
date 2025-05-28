package dao;

import model.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {
    private Connection conn;

    public RoomDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean addRoom(Room room) {
        String sql = "INSERT INTO rooms (room_number, type, price, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, room.getRoomNumber());
            stmt.setString(2, room.getType());
            stmt.setDouble(3, room.getPrice());
            stmt.setString(4, room.getStatus());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all rooms with a particular status
    public List<Room> getRoomsByStatus(String status) {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms WHERE status = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Room room = new Room(
                        rs.getInt("id"),
                        rs.getString("room_number"),
                        rs.getString("type"),
                        rs.getDouble("price"),
                        rs.getString("status")
                );
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    // Get all available rooms (helper method)
    public List<Room> getAvailableRooms() {
        return getRoomsByStatus("Available");
    }

    // Checkout room: set status from "Booked" to "Available"
    public boolean checkoutRoom(int roomId) throws SQLException {
        String sql = "UPDATE rooms SET status = 'Available' WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, roomId);
            int updatedRows = stmt.executeUpdate();
            return updatedRows > 0;
        }
    }

    // Set room availability status - true for Available, false for Booked
    public boolean setRoomAvailability(int roomId, boolean isAvailable) {
        String status = isAvailable ? "Available" : "Booked";
        String sql = "UPDATE rooms SET status = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, roomId);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
