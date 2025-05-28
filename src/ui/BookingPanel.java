package ui;

import dao.BookingDAO;
import dao.RoomDAO;
import model.Booking;
import model.Room;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class BookingPanel extends JPanel {
    private JComboBox<Room> roomComboBox;
    private JTextField customerNameField;
    private JTextField contactField;
    private JTextField checkInField;
    private JTextField checkOutField;
    private JTextField totalCostField;
    private JButton bookButton;
    private JTable bookingsTable;
    private DefaultTableModel tableModel;

    private RoomDAO roomDAO;
    private BookingDAO bookingDAO;

    public BookingPanel(Connection connection) {
        this.roomDAO = new RoomDAO(connection);
        this.bookingDAO = new BookingDAO(connection);

        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        inputPanel.add(new JLabel("Select Room:"));
        roomComboBox = new JComboBox<>();
        inputPanel.add(roomComboBox);

        inputPanel.add(new JLabel("Customer Name:"));
        customerNameField = new JTextField();
        inputPanel.add(customerNameField);

        inputPanel.add(new JLabel("Contact Number:"));
        contactField = new JTextField();
        inputPanel.add(contactField);

        inputPanel.add(new JLabel("Check-in Date (YYYY-MM-DD):"));
        checkInField = new JTextField();
        inputPanel.add(checkInField);

        inputPanel.add(new JLabel("Check-out Date (YYYY-MM-DD):"));
        checkOutField = new JTextField();
        inputPanel.add(checkOutField);

        inputPanel.add(new JLabel("Total Cost:"));
        totalCostField = new JTextField();
        totalCostField.setEditable(false); // Optional: auto-calculated
        inputPanel.add(totalCostField);

        bookButton = new JButton("Book Room");
        bookButton.addActionListener(e -> bookRoom());
        inputPanel.add(new JLabel());
        inputPanel.add(bookButton);

        add(inputPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
                new String[]{"Booking ID", "Room ID", "Customer", "Contact", "Check-In", "Check-Out", "Total Cost"},
                0
        );
        bookingsTable = new JTable(tableModel);
        add(new JScrollPane(bookingsTable), BorderLayout.CENTER);

        refreshAvailableRooms();
        refreshBookings();
    }

    private void refreshAvailableRooms() {
        roomComboBox.removeAllItems();
        List<Room> rooms = roomDAO.getAvailableRooms();
        for (Room room : rooms) {
            roomComboBox.addItem(room);
        }
    }

    private void refreshBookings() {
        tableModel.setRowCount(0);
        List<Booking> bookings = bookingDAO.getAllBookings();
        for (Booking b : bookings) {
            tableModel.addRow(new Object[]{
                    b.getId(),
                    b.getRoomId(),
                    b.getCustomerName(),
                    b.getContact(),
                    b.getCheckInDate(),
                    b.getCheckOutDate(),
                    b.getTotalCost()
            });
        }
    }

    private void bookRoom() {
        Room selectedRoom = (Room) roomComboBox.getSelectedItem();
        if (selectedRoom == null) {
            JOptionPane.showMessageDialog(this, "No available rooms to book!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String customer = customerNameField.getText().trim();
        String contact = contactField.getText().trim();

        if (customer.isEmpty() || contact.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Customer name and contact are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LocalDate checkIn, checkOut;
        try {
            checkIn = LocalDate.parse(checkInField.getText().trim());
            checkOut = LocalDate.parse(checkOutField.getText().trim());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid date format! Use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!checkOut.isAfter(checkIn)) {
            JOptionPane.showMessageDialog(this, "Check-out date must be after check-in date!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        long days = ChronoUnit.DAYS.between(checkIn, checkOut);
        double costPerDay = selectedRoom.getPrice(); // Make sure Room has a `getPrice()` method
        double totalCost = days * costPerDay;

        Booking booking = new Booking(0, selectedRoom.getId(), customer, contact, checkIn, checkOut, totalCost);
        boolean success = bookingDAO.addBooking(booking);

        if (success) {
            roomDAO.setRoomAvailability(selectedRoom.getId(), false);
            JOptionPane.showMessageDialog(this, "Room booked successfully!\nTotal Cost: ₹" + totalCost);

            refreshAvailableRooms();
            refreshBookings();

            customerNameField.setText("");
            contactField.setText("");
            checkInField.setText("");
            checkOutField.setText("");
            totalCostField.setText(String.valueOf(totalCost));
        } else {
            JOptionPane.showMessageDialog(this, "Booking failed!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
