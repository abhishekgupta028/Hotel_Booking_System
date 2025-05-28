package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.RoomDAO;
import model.Room;

public class CheckoutPanel extends JPanel {
    private RoomDAO roomDAO;
    private DefaultListModel<Room> bookedRoomListModel;
    private JList<Room> bookedRoomList;

    public CheckoutPanel(Connection conn) {
        this.roomDAO = new RoomDAO(conn);
        setLayout(new BorderLayout(10, 10));

        // List model and JList to show booked rooms
        bookedRoomListModel = new DefaultListModel<>();
        bookedRoomList = new JList<>(bookedRoomListModel);
        bookedRoomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(bookedRoomList), BorderLayout.CENTER);

        // Refresh button to reload booked rooms
        JButton refreshButton = new JButton("Refresh Booked Rooms");
        refreshButton.addActionListener(e -> loadBookedRooms());
        add(refreshButton, BorderLayout.NORTH);

        // Double-click to checkout
        bookedRoomList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    Room selectedRoom = bookedRoomList.getSelectedValue();
                    if (selectedRoom != null) {
                        int confirm = JOptionPane.showConfirmDialog(
                                CheckoutPanel.this,
                                "Do you want to check out Room " + selectedRoom.getRoomNumber() + "?",
                                "Confirm Checkout",
                                JOptionPane.YES_NO_OPTION
                        );

                        if (confirm == JOptionPane.YES_OPTION) {
                            try {
                                boolean success = roomDAO.checkoutRoom(selectedRoom.getId());
                                if (success) {
                                    JOptionPane.showMessageDialog(CheckoutPanel.this,
                                            "Room checked out successfully!");
                                    loadBookedRooms();  // Refresh list
                                } else {
                                    JOptionPane.showMessageDialog(CheckoutPanel.this,
                                            "Failed to check out the room.", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                                JOptionPane.showMessageDialog(CheckoutPanel.this,
                                        "Error during checkout: " + e.getMessage(),
                                        "Database Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }
            }
        });

        // Load booked rooms when panel is initialized
        loadBookedRooms();
    }

    // Public method so it can be called externally to refresh the panel
    public void loadBookedRooms() {
        try {
            List<Room> bookedRooms = roomDAO.getRoomsByStatus("Booked");
            bookedRoomListModel.clear();
            for (Room room : bookedRooms) {
                bookedRoomListModel.addElement(room);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error loading booked rooms: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
