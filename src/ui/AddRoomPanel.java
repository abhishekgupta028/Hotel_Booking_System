package ui;

import javax.swing.*;
import java.awt.*;
import model.Room;
import dao.RoomDAO;
import util.DatabaseUtil;

public class AddRoomPanel extends JPanel {

    private JTextField roomNumberField;
    private JTextField typeField;
    private JTextField statusField;
    private JTextField priceField;

    public AddRoomPanel() {
        setLayout(new BorderLayout());

        // Panel to hold form fields
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        formPanel.add(new JLabel("Room Number:"));
        roomNumberField = new JTextField();
        formPanel.add(roomNumberField);

        formPanel.add(new JLabel("Type:"));
        typeField = new JTextField();
        formPanel.add(typeField);

        formPanel.add(new JLabel("Status:"));
        statusField = new JTextField();
        formPanel.add(statusField);

        formPanel.add(new JLabel("Price:"));
        priceField = new JTextField();
        formPanel.add(priceField);

        JButton addButton = new JButton("Add Room");
        addButton.addActionListener(e -> addRoom());

        formPanel.add(new JLabel()); // empty cell
        formPanel.add(addButton);

        add(formPanel, BorderLayout.CENTER);
    }

    private void addRoom() {
        try {
            String roomNumber = roomNumberField.getText().trim();
            String type = typeField.getText().trim();
            String status = statusField.getText().trim();
            double price = Double.parseDouble(priceField.getText().trim());

            Room room = new Room(0, roomNumber, type, status, price);
            RoomDAO roomDAO = new RoomDAO(DatabaseUtil.getConnection());
            boolean success = roomDAO.addRoom(room);

            if (success) {
                JOptionPane.showMessageDialog(this, "Room added successfully!");
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add room.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid price entered.", "Input Error", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        roomNumberField.setText("");
        typeField.setText("");
        statusField.setText("");
        priceField.setText("");
    }


}
