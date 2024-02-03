import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI {

    private JFrame frame;
    private JTextField usernameField;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new MainGUI().initialize();
        });
    }

    public void initialize() {
        frame = new JFrame("Login");
        frame.setBounds(100, 120, 400, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        placeComponents(panel);

        // Add an exit button to the top right side
        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(320, 10, 60, 25);
        panel.add(exitButton);

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);
        /*
         * 
         * ImageIcon logoIcon = new ImageIcon("logo.jpeg");
         * JLabel logoLabel = new JLabel(logoIcon);
         * logoLabel.setBounds(50, 10, 302, 337);
         * panel.add(logoLabel);
         */

        JLabel reservationLabel = new JLabel("<html>University Venue Reservation System</html>");
        reservationLabel.setFont(new Font("Arial", Font.BOLD, 18));
        reservationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        reservationLabel.setBounds(50, 60, 300, 50);
        panel.add(reservationLabel);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(10, 100, 80, 25);
        panel.add(userLabel);

        usernameField = new JTextField(20);
        usernameField.setBounds(100, 100, 250, 25);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 130, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setBounds(100, 130, 250, 25);
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(160, 160, 80, 25);
        panel.add(loginButton);

        JLabel errorMsgLabel = new JLabel("");
        errorMsgLabel.setForeground(Color.RED);
        errorMsgLabel.setFont(new Font("Arial", Font.BOLD, 12));
        errorMsgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errorMsgLabel.setBounds(10, 290, 300, 25);
        panel.add(errorMsgLabel);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.equals("admin") && password.equals("admin")) {
                    frame.dispose();
                    ReservationGUI.display();
                } else {
                    errorMsgLabel.setText("Wrong username or password. Please try again.");
                }
            }
        });
    }
}
