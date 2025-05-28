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
        setLayout(new BorderLayout());

        // List to display booked rooms
        bookedRoomListModel = new DefaultListModel<>();
        bookedRoomList = new JList<>(bookedRoomListModel);
        add(new JScrollPane(bookedRoomList), BorderLayout.CENTER);

        // Refresh button
        JButton refreshButton = new JButton("Refresh Booked Rooms");
        refreshButton.addActionListener(e -> loadBookedRooms());
        add(refreshButton, BorderLayout.NORTH);

        // Double-click to checkout room
        bookedRoomList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    Room selectedRoom = bookedRoomList.getSelectedValue();
                    if (selectedRoom != null) {
                        int confirm = JOptionPane.showConfirmDialog(
                                CheckoutPanel.this,
                                "Do you want to check out room " + selectedRoom.getRoomNumber() + "?",
                                "Confirm Checkout",
                                JOptionPane.YES_NO_OPTION
                        );

                        if (confirm == JOptionPane.YES_OPTION) {
                            try {
                                boolean checkedOut = roomDAO.checkoutRoom(selectedRoom.getId());
                                if (checkedOut) {
                                    JOptionPane.showMessageDialog(CheckoutPanel.this, "Room checked out successfully!");
                                    loadBookedRooms();
                                } else {
                                    JOptionPane.showMessageDialog(CheckoutPanel.this, "Failed to check out room.");
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                                JOptionPane.showMessageDialog(CheckoutPanel.this, "Error checking out room: " + e.getMessage());
                            }
                        }
                    }
                }
            }
        });


        // Load booked rooms initially
        loadBookedRooms();
    }

    // ✅ FIXED: Removed `throws SQLException` from here
    private void loadBookedRooms() {
        try {
            List<Room> bookedRooms = roomDAO.getRoomsByStatus("Booked");
            bookedRoomListModel.clear();
            for (Room room : bookedRooms) {
                bookedRoomListModel.addElement(room);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading booked rooms: " + e.getMessage());
        }
    }
}
