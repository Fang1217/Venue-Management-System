import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class ReservationGUI {
    private static JFrame venueManagementFrame;
    private static ReservationManager reservationManager = new ReservationManager();

    private static JComboBox<String> venueIDComboBox = new JComboBox<>();

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

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton logoutButton = new JButton("Log Out");
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
        displayArea.setText(reservationManager.displayReservation());
        displayArea.setEditable(false);

        reservationManager = new ReservationManager();

        JPanel fieldPanel = new JPanel(new GridLayout(0, 2));
        fieldPanel.add(new JLabel("Venue ID:"));

        for (Venue v : reservationManager.venues) {
            venueIDComboBox.addItem(v.venueID);
        }

        fieldPanel.add(venueIDComboBox);
        fieldPanel.add(new JLabel("Date (YYYY/MM/DD):"));

        JTextField dateField;
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
                String venueID = (String) venueIDComboBox.getSelectedItem();
                String date = dateField.getText();
                String time = timeChoice.getSelectedItem();
                if (reservationManager.isValidReservationInput(venueID, date, time)) {
                    reservationManager.addReservation(venueID, date, time);
                    displayArea.setText(reservationManager.displayReservation());
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid input! Please check the fields.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton deleteButton = new JButton("Delete Reservation");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String venueID = (String) venueIDComboBox.getSelectedItem();
                String date = dateField.getText();
                String time = timeChoice.getSelectedItem();
                if (reservationManager.isValidReservationInput(venueID, date, time)) {
                    reservationManager.deleteReservation(venueID, date, time);
                    displayArea.setText(reservationManager.displayReservation());
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid input! Please check the fields.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
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

        NumberFormat format = NumberFormat.getInstance();
        format.setGroupingUsed(false);// Remove comma from number greater than 4 digit
        NumberFormatter numberFormatter = new NumberFormatter(format);
        numberFormatter.setValueClass(Integer.class);
        numberFormatter.setMinimum(0);
        numberFormatter.setMaximum(9999);

        JFormattedTextField maxCapacityField = new JFormattedTextField(numberFormatter);
        maxCapacityField.setText("0");

        fieldPanel.add(venueIdLabel);
        fieldPanel.add(venueIdField);
        fieldPanel.add(maxCapacityLabel);
        fieldPanel.add(maxCapacityField);

        JLabel venueFunctionLabel = new JLabel("Venue Function:");
        JRadioButton lectureHallButton = new JRadioButton("Lecture Hall", true);
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

        ButtonGroup venueFunctionGroup = new ButtonGroup();
        venueFunctionGroup.add(lectureHallButton);
        venueFunctionGroup.add(tutorialRoomButton);
        venueFunctionGroup.add(labRoomButton);
        venueFunctionGroup.add(courtButton);
        venueFunctionGroup.add(otherVenueButton);

        JPanel venueFunctionPanel = new JPanel(new GridLayout(7, 2));
        venueFunctionPanel.add(venueFunctionLabel);
        venueFunctionPanel.add(new JPanel());
        venueFunctionPanel.add(lectureHallButton);
        venueFunctionPanel.add(new JPanel());
        venueFunctionPanel.add(tutorialRoomButton);
        venueFunctionPanel.add(new JPanel());
        venueFunctionPanel.add(labRoomButton);
        venueFunctionPanel.add(new JPanel());
        venueFunctionPanel.add(courtButton);
        venueFunctionPanel.add(new JPanel());
        venueFunctionPanel.add(otherVenueButton);
        venueFunctionPanel.add(otherVenueFunctionField);

        // Display panel, containing table
        JTextArea displayArea = new JTextArea();
        displayArea.setText(reservationManager.displayVenue());
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
                String venueFunction = otherVenueButton.isSelected() ? otherVenueFunctionField.getText()
                        : venueFunctionGroup.getSelection().getActionCommand();

                if (!reservationManager.isValidVenueInput(venueId, maxCapacity, venueFunction)) {
                    JOptionPane.showMessageDialog(null, "Invalid input! Please check the fields.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                reservationManager.addVenue(venueId, maxCapacity, venueFunction);
                displayArea.setText(reservationManager.displayVenue());
                // Updates the Combo Box in the Venue ID list in Reservation Page.
                venueIDComboBox.removeAllItems();
                for (Venue v : reservationManager.venues) {
                    venueIDComboBox.addItem(v.venueID);
                }
            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String venueId = venueIdField.getText();
                int maxCapacity = Integer.parseInt(maxCapacityField.getText());
                String venueFunction = otherVenueButton.isSelected() ? otherVenueFunctionField.getText()
                        : venueFunctionGroup.getSelection().getActionCommand();

                if (!reservationManager.isValidVenueInput(venueId, maxCapacity, venueFunction)) {
                    JOptionPane.showMessageDialog(null, "Invalid input! Please check the fields.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                reservationManager.editVenue(venueId, maxCapacity, venueFunction);
                displayArea.setText(reservationManager.displayVenue());
                // Updates the Combo Box in the Venue ID list in Reservation Page.
                venueIDComboBox.removeAllItems();
                for (Venue v : reservationManager.venues) {
                    venueIDComboBox.addItem(v.venueID);
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String venueId = venueIdField.getText();
                reservationManager.deleteVenue(venueId);
                displayArea.setText(reservationManager.displayVenue());
                // Updates the Combo Box in the Venue ID list in Reservation Page.
                venueIDComboBox.removeAllItems();
                for (Venue v : reservationManager.venues) {
                    venueIDComboBox.addItem(v.venueID);
                }
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

    static protected MaskFormatter createFormatter(String s) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(s);
        } catch (java.text.ParseException exc) {
            System.err.println("formatter is bad: " + exc.getMessage());
            System.exit(-1);
        }
        return formatter;
    }
}
