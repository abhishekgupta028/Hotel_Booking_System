package dao;

import model.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {
    private final Connection connection;

    public RoomDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Room> getAvailableRooms() {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms WHERE is_available = true";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Room room = new Room(
                        rs.getInt("id"),
                        rs.getString("room_number"),
                        rs.getString("type"),
                        rs.getString("status"),
                        rs.getDouble("price")
                );
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    public List<Room> getRoomsByStatus(String status) {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms WHERE status = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Room room = new Room(
                            rs.getInt("id"),
                            rs.getString("room_number"),
                            rs.getString("type"),
                            rs.getString("status"),
                            rs.getDouble("price")
                    );
                    rooms.add(room);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    public boolean addRoom(Room room) {
        String sql = "INSERT INTO rooms (room_number, type, status, price, is_available) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, room.getRoomNumber());
            stmt.setString(2, room.getType());
            stmt.setString(3, room.getStatus());
            stmt.setDouble(4, room.getPrice());
            stmt.setBoolean(5, true); // assuming new rooms are available by default
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setRoomAvailability(int roomId, boolean available) {
        String sql = "UPDATE rooms SET is_available = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setBoolean(1, available);
            stmt.setInt(2, roomId);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to checkout a room (make it available and update status)
    public boolean checkoutRoom(int roomId) throws SQLException {
        String sql = "UPDATE rooms SET status = 'Available', is_available = true WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, roomId);
            return stmt.executeUpdate() > 0;
        }
    }

    // You may want to add a method to book a room (set status = 'Booked' and is_available = false)
    public boolean bookRoom(int roomId) throws SQLException {
        String sql = "UPDATE rooms SET status = 'Booked', is_available = false WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, roomId);
            return stmt.executeUpdate() > 0;
        }
    }
}
