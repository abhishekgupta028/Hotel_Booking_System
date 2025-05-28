
import ui.CheckoutPanel;
import dao.DBConnection;
import ui.BookingPanel;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Connection conn = DBConnection.getConnection();

                JFrame frame = new JFrame("Hotel Booking System");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);
                frame.setLayout(new BorderLayout());

                JTabbedPane tabs = new JTabbedPane();
                tabs.add("Booking", new BookingPanel(conn));
                tabs.add("Checkout", new CheckoutPanel(conn));

                frame.add(tabs);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error initializing application: " + e.getMessage());
            }
        });
    }
}
