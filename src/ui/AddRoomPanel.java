package ui;

import dao.RoomDAO;
import model.Room;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class AddRoomPanel extends JPanel {
    private JTextField roomNumberField;
    private JTextField typeField;
    private JTextField priceField;
    private JButton addButton;

    private RoomDAO roomDAO;

    public AddRoomPanel(Connection conn) {
        this.roomDAO = new RoomDAO(conn);
        setLayout(new GridLayout(5, 2, 10, 10));

        JLabel roomNumberLabel = new JLabel("Room Number:");
        roomNumberField = new JTextField();

        JLabel typeLabel = new JLabel("Room Type:");
        typeField = new JTextField();

        JLabel priceLabel = new JLabel("Price:");
        priceField = new JTextField();

        addButton = new JButton("Add Room");

        add(roomNumberLabel);
        add(roomNumberField);
        add(typeLabel);
        add(typeField);
        add(priceLabel);
        add(priceField);
        add(new JLabel()); // empty cell
        add(addButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int roomNumber = Integer.parseInt(roomNumberField.getText());
                    String type = typeField.getText();
                    double price = Double.parseDouble(priceField.getText());

                    Room room = new Room(0, roomNumber, type, price, "Available");
                    boolean success = roomDAO.addRoom(room);

                    if (success) {
                        JOptionPane.showMessageDialog(AddRoomPanel.this, "Room added successfully!");
                        roomNumberField.setText("");
                        typeField.setText("");
                        priceField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(AddRoomPanel.this, "Failed to add room.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(AddRoomPanel.this, "Please enter valid values.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(AddRoomPanel.this, "Error: " + ex.getMessage());
                }
            }
        });
    }
}
