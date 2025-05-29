import dao.BookingDAO;
import dao.DBConnection;
import dao.RoomDAO;
import ui.BookingPanel;
import ui.CheckoutPanel;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Connection conn = DBConnection.getConnection();

                // Instantiate DAO objects
                RoomDAO roomDAO = new RoomDAO(conn);
                BookingDAO bookingDAO = new BookingDAO(conn);

                JFrame frame = new JFrame("Hotel Booking System");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);
                frame.setLayout(new BorderLayout());

                JTabbedPane tabs = new JTabbedPane();

                // Pass the DAO objects to the panels
                tabs.add("Booking", new BookingPanel(roomDAO, bookingDAO));
                tabs.add("Checkout", new CheckoutPanel(conn)); // Assuming CheckoutPanel still takes Connection

                frame.add(tabs);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error initializing application: " + e.getMessage());
            }
        });
    }
}
