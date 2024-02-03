import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReservationGUI {
    private static JFrame venueManagementFrame;

    private static ReservationManager reservationManager = new ReservationManager();

    public static void display() {
        venueManagementFrame = new JFrame("University Venue Management System");
        venueManagementFrame.setBounds(100, 100, 800, 600);
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
        // JButton checkSalesButton = new JButton("Check Past Sales");

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
        JPanel tabPanel = new JPanel(new GridLayout(1, 2, 20, 50));
        
        JPanel leftPanel = new JPanel(new GridLayout(0, 1, 20, 50));
    
        // Display panel, containing table
        JTextArea displayArea = new JTextArea();
        displayArea.setText(reservationManager.displayReservations());
        displayArea.setEditable(false);

        reservationManager = new ReservationManager();
        
        JPanel fieldPanel = new JPanel(new GridLayout(0, 2));
        
        JTextField venueIDField, dateField;
        fieldPanel.add(new JLabel("Venue ID:"));
        venueIDField = new JTextField();
        fieldPanel.add(venueIDField);
        fieldPanel.add(new JLabel("Date:"));
        dateField = new JTextField();
        fieldPanel.add(dateField);

        fieldPanel.add(new JLabel("Time:"));
        Choice timeChoice = new Choice();
        timeChoice.add("8am - 10am");
        timeChoice.add("10am - 12pm");
        timeChoice.add("12pm - 2pm");
        timeChoice.add("2pm - 4pm");
        timeChoice.add("4pm - 6pm");

        fieldPanel.add(timeChoice);
        fieldPanel.add(new JLabel(""));
        fieldPanel.add(new JLabel(""));
        
        JButton addButton = new JButton("Add Reservation");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String venueID = venueIDField.getText();
                String date = dateField.getText();
                String time = timeChoice.getSelectedItem();
                reservationManager.addReservation(venueID, date, time);
                displayArea.setText(reservationManager.displayReservations());
            }
        });

        JButton deleteButton = new JButton("Delete Reservation");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String venueID = venueIDField.getText();
                String date = dateField.getText();
                String time = timeChoice.getSelectedItem();
                reservationManager.deleteReservation(venueID, date, time);
                displayArea.setText(reservationManager.displayReservations());
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);

        displayArea.setEditable(false);

        leftPanel.add(fieldPanel);
        leftPanel.add(buttonPanel);

        tabPanel.add(leftPanel);
        JScrollPane displayPane = new JScrollPane(displayArea);
        tabPanel.add(displayPane);
        return tabPanel;

    }

    private static JPanel VenueManagement() {
        JPanel tabPanel = new JPanel(new GridLayout(1, 2, 20, 50));

        JPanel leftPanel = new JPanel(new GridLayout(0, 1, 20, 50));

        // Field panel, containing fields
        JPanel fieldPanel = new JPanel(new GridLayout(2, 2));

        JLabel venueIdLabel = new JLabel("Venue ID:");
        JTextField venueIdField = new JTextField();
        JLabel maxCapacityLabel = new JLabel("Max Capacity:");
        JTextField maxCapacityField = new JTextField();
        JLabel venueFunctionLabel = new JLabel("Venue Function:");
        fieldPanel.add(venueIdLabel);
        fieldPanel.add(venueIdField);
        fieldPanel.add(maxCapacityLabel);
        fieldPanel.add(maxCapacityField);

        ButtonGroup venueFunctionGroup = new ButtonGroup();
        JRadioButton lectureHallButton = new JRadioButton("Lecture Hall");
        lectureHallButton.setActionCommand("Lecture Hall");
        JRadioButton tutorialRoomButton = new JRadioButton("Tutorial Room");
        tutorialRoomButton.setActionCommand("Tutorial Room");
        JRadioButton labRoomButton = new JRadioButton("Lab Room");
        labRoomButton.setActionCommand("Lab Room");
        JRadioButton courtButton = new JRadioButton("Court");
        courtButton.setActionCommand("Court");
        JRadioButton otherVenueButton = new JRadioButton("Other");
        otherVenueButton.setActionCommand("Other");
        JTextField otherVenueFunctionField;
        otherVenueFunctionField = new JTextField();

        venueFunctionGroup.add(lectureHallButton);
        venueFunctionGroup.add(tutorialRoomButton);
        venueFunctionGroup.add(labRoomButton);
        venueFunctionGroup.add(courtButton);
        venueFunctionGroup.add(otherVenueButton);

        JPanel venueFunctionPanel = new JPanel(new GridLayout(7, 2));
        venueFunctionPanel.add(venueFunctionLabel);
        venueFunctionPanel.add(new JPanel());
        venueFunctionPanel.add(labRoomButton);
        venueFunctionPanel.add(new JPanel());
        venueFunctionPanel.add(tutorialRoomButton);
        venueFunctionPanel.add(new JPanel());
        venueFunctionPanel.add(lectureHallButton);
        venueFunctionPanel.add(new JPanel());
        venueFunctionPanel.add(courtButton);
        venueFunctionPanel.add(new JPanel());
        venueFunctionPanel.add(otherVenueButton);
        venueFunctionPanel.add(otherVenueFunctionField);

        // Display panel, containing table
        JTextArea displayArea = new JTextArea();
        displayArea.setText(reservationManager.displayVenues());
        displayArea.setEditable(false);

        otherVenueFunctionField.setEnabled(false); // Initially disable the text field
        lectureHallButton.addActionListener(e -> otherVenueFunctionField.setEnabled(false));
        tutorialRoomButton.addActionListener(e -> otherVenueFunctionField.setEnabled(false));
        labRoomButton.addActionListener(e -> otherVenueFunctionField.setEnabled(false));
        courtButton.addActionListener(e -> otherVenueFunctionField.setEnabled(false));
        otherVenueButton.addActionListener(e -> otherVenueFunctionField.setEnabled(true));

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Venue");
        buttonPanel.add(addButton);
        JButton editButton = new JButton("Edit Venue");
        buttonPanel.add(editButton);
        JButton deleteButton = new JButton("Delete Venue");
        buttonPanel.add(deleteButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String venueId = venueIdField.getText();
                int maxCapacity = Integer.parseInt(maxCapacityField.getText());
                String venueFunction;
                if (otherVenueButton.isSelected()) {
                    venueFunction = otherVenueFunctionField.getText();
                } else {
                    venueFunction = venueFunctionGroup.getSelection().getActionCommand();
                }
                reservationManager.addVenue(venueId, maxCapacity, venueFunction);
                displayArea.setText(reservationManager.displayReservations());
            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String venueId = venueIdField.getText();
                int maxCapacity = Integer.parseInt(maxCapacityField.getText());
                String venueFunction;
                if (otherVenueButton.isSelected()) {
                    venueFunction = otherVenueFunctionField.getText();
                } else {
                    venueFunction = venueFunctionGroup.getSelection().getActionCommand();
                }
                reservationManager.editVenue(venueId, maxCapacity, venueFunction);
                displayArea.setText(reservationManager.displayVenues());
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String venueId = venueIdField.getText();
                reservationManager.deleteVenue(venueId);
                displayArea.setText(reservationManager.displayVenues());
            }
        });

        leftPanel.add(fieldPanel);
        leftPanel.add(venueFunctionPanel);
        leftPanel.add(buttonPanel);

        tabPanel.add(leftPanel);
        JScrollPane displayPane = new JScrollPane(displayArea);
        tabPanel.add(displayPane);
        tabPanel.setVisible(true);

        return tabPanel;

    }

    private static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
        return sdf.format(new Date());
    }

}
