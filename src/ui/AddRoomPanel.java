package ui;

import dao.RoomDAO;
import model.Room;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class AddRoomPanel extends JPanel {
    private JTextField roomNumberField;
    private JTextField roomTypeField;
    private JTextField priceField;
    private JButton addButton;

    private RoomDAO roomDAO;

    public AddRoomPanel(Connection connection) {
        this.roomDAO = new RoomDAO(connection);

        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("Room Number:"));
        roomNumberField = new JTextField();
        add(roomNumberField);

        add(new JLabel("Room Type:"));
        roomTypeField = new JTextField();
        add(roomTypeField);

        add(new JLabel("Price:"));
        priceField = new JTextField();
        add(priceField);

        addButton = new JButton("Add Room");
        addButton.addActionListener(e -> addRoom());
        add(new JLabel()); // empty label for layout
        add(addButton);
    }

    private void addRoom() {
        String roomNumber = roomNumberField.getText().trim();
        String roomType = roomTypeField.getText().trim();
        String priceText = priceField.getText().trim();

        if (roomNumber.isEmpty() || roomType.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid price. Enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Room room = new Room(0, roomNumber, roomType, price, "Available"); // assuming id=0 means new room, status "Available"

        boolean success = roomDAO.addRoom(room);

        if (success) {
            JOptionPane.showMessageDialog(this, "Room added successfully!");
            roomNumberField.setText("");
            roomTypeField.setText("");
            priceField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add room.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
