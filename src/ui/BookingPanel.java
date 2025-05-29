package ui;

import dao.BookingDAO;
import dao.RoomDAO;
import model.Booking;
import model.Room;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class BookingPanel extends JPanel {
    private final RoomDAO roomDAO;
    private final BookingDAO bookingDAO;
    private final DefaultListModel<Booking> bookingListModel;
    private final JList<Booking> bookingList;

    private final JComboBox<Room> roomComboBox;
    private final JTextField nameField, contactField, checkInField, checkOutField;

    public BookingPanel(RoomDAO roomDAO, BookingDAO bookingDAO) {
        this.roomDAO = roomDAO;
        this.bookingDAO = bookingDAO;

        setLayout(new BorderLayout());

        // TOP PANEL: Booking Form
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Book a Room"));

        roomComboBox = new JComboBox<>();
        nameField = new JTextField();
        contactField = new JTextField();
        checkInField = new JTextField("YYYY-MM-DD");
        checkOutField = new JTextField("YYYY-MM-DD");

        formPanel.add(new JLabel("Room:"));
        formPanel.add(roomComboBox);
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Contact:"));
        formPanel.add(contactField);
        formPanel.add(new JLabel("Check-In Date:"));
        formPanel.add(checkInField);
        formPanel.add(new JLabel("Check-Out Date:"));
        formPanel.add(checkOutField);

        JButton bookButton = new JButton("Book Room");
        formPanel.add(bookButton);

        JButton refreshRoomsButton = new JButton("Refresh Available Rooms");
        formPanel.add(refreshRoomsButton);

        add(formPanel, BorderLayout.NORTH);

        // CENTER PANEL: Booking List
        bookingListModel = new DefaultListModel<>();
        bookingList = new JList<>(bookingListModel);
        bookingList.setBorder(BorderFactory.createTitledBorder("Current Bookings"));
        add(new JScrollPane(bookingList), BorderLayout.CENTER);

        // Load data
        loadAvailableRooms();
        loadBookings();

        // Event Listeners
        bookButton.addActionListener(e -> bookRoom());
        refreshRoomsButton.addActionListener(e -> loadAvailableRooms());
    }

    private void bookRoom() {
        Room selectedRoom = (Room) roomComboBox.getSelectedItem();
        String name = nameField.getText().trim();
        String contact = contactField.getText().trim();
        String checkIn = checkInField.getText().trim();
        String checkOut = checkOutField.getText().trim();

        if (selectedRoom == null || name.isEmpty() || contact.isEmpty() || checkIn.isEmpty() || checkOut.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields and select a room.");
            return;
        }

        try {
            LocalDate checkInDate = LocalDate.parse(checkIn);
            LocalDate checkOutDate = LocalDate.parse(checkOut);

            Booking booking = new Booking(
                    0,
                    selectedRoom.getId(),
                    name,
                    contact,
                    checkInDate,
                    checkOutDate,
                    selectedRoom.getPrice()
            );
            booking.setRoomNumber(String.valueOf(selectedRoom.getRoomNumber())); // For display

            boolean success = bookingDAO.addBooking(booking);
            if (success) {
                roomDAO.setRoomAvailability(selectedRoom.getId(), false); // Set room to Booked
                JOptionPane.showMessageDialog(this, "Room booked successfully!");
                clearForm();
                loadAvailableRooms();
                loadBookings();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to book room.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Invalid input: " + e.getMessage());
        }
    }

    private void loadAvailableRooms() {
        try {
            roomComboBox.removeAllItems();
            List<Room> availableRooms = roomDAO.getAvailableRooms();
            for (Room room : availableRooms) {
                roomComboBox.addItem(room);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading rooms: " + e.getMessage());
        }
    }

    private void loadBookings() {
        try {
            bookingListModel.clear();
            List<Booking> bookings = bookingDAO.getAllBookings();
            for (Booking booking : bookings) {
                Room room = roomDAO.getRoomById(booking.getRoomId());
                booking.setRoomNumber(String.valueOf(room.getRoomNumber()));
                bookingListModel.addElement(booking);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading bookings: " + e.getMessage());
        }
    }

    private void clearForm() {
        nameField.setText("");
        contactField.setText("");
        checkInField.setText("YYYY-MM-DD");
        checkOutField.setText("YYYY-MM-DD");
    }
}
