import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import javax.swing.*;
import java.util.*;

public class ReservationGUI {
    private static JFrame venueManagementFrame;

    private static VenueManager venueManager = new VenueManager();
    private static ReservationManager reservationManager = new ReservationManager();


    public static void display() {
        venueManagementFrame = new JFrame("University Venue Management System");
        venueManagementFrame.setBounds(100, 100, 1000, 800);
        venueManagementFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Reservation Management", ReservationManager());
        tabbedPane.addTab("Venue Management", VenueManagement());

        // Add the tabbed pane to the frame
        venueManagementFrame.getContentPane().add(tabbedPane);

        // Create the welcome panel
        JPanel topPanel = topPanel();
        venueManagementFrame.getContentPane().add(topPanel, BorderLayout.NORTH);

        // Set the frame visible
        venueManagementFrame.setVisible(true);

    }

    private static JPanel topPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Top Panel for the "Cart" button (aligned to the right)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        // ImageIcon cartIcon = new ImageIcon("images/cart.png");
        // JButton cartButton = new JButton("CART", cartIcon);
        // topPanel.add(cartButton);

        // Center Panel for the "Welcome" and "Current Time" labels
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JLabel welcomeLabel = new JLabel("Welcome to University Venue Management System!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(welcomeLabel);

        JLabel timeLabel = new JLabel("Current Time: " + getCurrentTime());
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(timeLabel);

        // Bottom Panel for "Log Out" and "Check Past Sales" buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton logoutButton = new JButton("Log Out");
        //JButton checkSalesButton = new JButton("Check Past Sales");

        bottomPanel.add(logoutButton);

        // Add the panels to the main panel
        panel.add(topPanel, BorderLayout.PAGE_START);
        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.PAGE_END);

        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                venueManagementFrame.dispose();
                new MainGUI().initialize();
            }
        });

        // Use Timer to update time every second
        final javax.swing.Timer timer = new javax.swing.Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLabel.setText("Current Time: " + getCurrentTime());
            }
        });
        timer.start();
        return panel;
    }

    private static JPanel ReservationManager() {
        JPanel tabPanel = new JPanel(new GridLayout(0, 3, 20, 50));
        JTextField venueIDField, dateField, timeField;
        JTextArea displayArea = new JTextArea();
        reservationManager = new ReservationManager();

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));

        inputPanel.add(new JLabel("Venue ID:"));
        venueIDField = new JTextField();
        inputPanel.add(venueIDField);

        inputPanel.add(new JLabel("Date:"));
        dateField = new JTextField();
        inputPanel.add(dateField);
        
        inputPanel.add(new JLabel("Time:"));
        timeField = new JTextField();
        inputPanel.add(timeField);

        JButton addButton = new JButton("Add Reservation");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String venueID = venueIDField.getText();
                String date = dateField.getText();
                String time = timeField.getText();
                reservationManager.add(venueID, date, time);
                displayArea.setText(reservationManager.display());
            }
        });

        JButton deleteButton = new JButton("Delete Reservation");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String venueID = venueIDField.getText();
                String date = dateField.getText();
                String time = timeField.getText();
                reservationManager.delete(venueID, date, time);
                displayArea.setText(reservationManager.display());
            }
        });

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);

        displayArea.setEditable(false);

        tabPanel.setLayout(new BorderLayout());
        tabPanel.add(inputPanel, BorderLayout.NORTH);
        tabPanel.add(buttonPanel, BorderLayout.CENTER);
        tabPanel.add(displayArea, BorderLayout.SOUTH);
        return tabPanel;

    }

    private static JPanel VenueManagement() {
        JPanel tabPanel = new JPanel(new GridLayout(0, 3, 20, 50));
        JTextField venueIdField;
        JTextField maxCapacityField;
        JTextField venueFunctionField;
        JTextArea displayArea = new JTextArea();

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(5, 2));

        JLabel venueIdLabel = new JLabel("Venue ID:");
        controlPanel.add(venueIdLabel);
        venueIdField = new JTextField();
        controlPanel.add(venueIdField);

        JLabel maxCapacityLabel = new JLabel("Max Capacity:");
        controlPanel.add(maxCapacityLabel);
        maxCapacityField = new JTextField();
        controlPanel.add(maxCapacityField);

        JLabel venueFunctionLabel = new JLabel("Venue Function:");
        controlPanel.add(venueFunctionLabel);
        venueFunctionField = new JTextField();
        controlPanel.add(venueFunctionField);

        JButton addButton = new JButton("Add Venue");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String venueId = venueIdField.getText();
                int maxCapacity = Integer.parseInt(maxCapacityField.getText());
                String venueFunction = venueFunctionField.getText();

                // Assuming you have a method in VenueManager to add a venue
                // venueManager.addVenue(venueId, maxCapacity, venueFunction);
                // You would then update the display accordingly
                // displayArea.setText(venueManager.display());
            }
        });
        controlPanel.add(addButton);

        JButton displayButton = new JButton("Display Venues");
        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayArea.setText(venueManager.display());
            }
        });
        controlPanel.add(displayButton);

        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        tabPanel.add(controlPanel, BorderLayout.NORTH);
        tabPanel.add(scrollPane, BorderLayout.CENTER);

        tabPanel.setVisible(true);
        return tabPanel;
    }

    private static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(new Date());
    }
}
