package dao;

import model.Booking;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {
    private final Connection connection;

    public BookingDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Booking booking = new Booking(
                        rs.getInt("id"),
                        rs.getInt("room_id"),
                        rs.getString("customer_name"),
                        rs.getString("contact"),
                        rs.getDate("checkin_date").toLocalDate(),
                        rs.getDate("checkout_date").toLocalDate(),
                        rs.getDouble("total_cost")
                );
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public boolean addBooking(Booking booking) {
        String sql = "INSERT INTO bookings (customer_name, contact, room_id, checkin_date, checkout_date, total_cost) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, booking.getCustomerName());
            stmt.setString(2, booking.getContact());
            stmt.setInt(3, booking.getRoomId());
            stmt.setDate(4, Date.valueOf(booking.getCheckInDate()));
            stmt.setDate(5, Date.valueOf(booking.getCheckOutDate()));
            stmt.setDouble(6, booking.getTotalCost());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
