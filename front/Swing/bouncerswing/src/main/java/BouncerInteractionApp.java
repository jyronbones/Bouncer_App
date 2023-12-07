import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * A class that contains all necessary components for running a swing app that
 * displays all bouncers in a local database. App can also update bouncer values
 * then refresh the displayed list to reflect those changes.
 *
 * @author Sean Wray
 */
public class BouncerInteractionApp extends JFrame {

    private JTable bouncersTable;             // used to display all bouncers in database  
    private final JButton updateButton;       // update a selected bouncer
    private final JButton refreshButton;      // refreshes bouncer list
    private int selectedBouncerId = -1;       // user-selected bouncer ID
    private final int maxY = 600;             // max Y value for a bouncer location
    private final int minY = 0;               // min Y value for a bouncer location
    private final int maxX = 800;             // max X value for a bouncer location
    private final int minX = 0;               // min X value for a bouncer location
    private final String urlString = "http://localhost:8080/bouncer-fearnall/resources/bouncer/";
    private String username;                  // store entered username
    private String password;                  // store entered password

    /**
     * Constructor for setting up JFrame, UI components, listeners, and calling
     * the displayBouncers() function upon app startup
     */
    public BouncerInteractionApp() {
        // Display login screen
        showLoginScreen();

        // Setup the main UI after successful login
        setTitle("CST8218 Java Swing Bouncers A2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        UIManager.put("Panel.background", Color.BLACK);
        UIManager.put("Panel.foreground", Color.WHITE);
        UIManager.put("Button.background", Color.BLACK);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Table.background", Color.BLACK);
        UIManager.put("Table.foreground", Color.WHITE);
        UIManager.put("OptionPane.messageForeground", Color.WHITE);
        JPanel panel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("BOUNCER INFO");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel, BorderLayout.NORTH);

        // Table for displaying bouncers and their attributes
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("X");
        model.addColumn("Y");
        model.addColumn("Velocity");

        bouncersTable = new JTable(model);
        bouncersTable.setBackground(Color.BLACK);
        bouncersTable.setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(bouncersTable);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < bouncersTable.getColumnCount(); i++) {
            bouncersTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        panel.add(scrollPane, BorderLayout.CENTER);

        updateButton = new JButton("Update Selected Bouncer");
        updateButton.addActionListener(e -> updateSelectedBouncer());
        updateButton.setBackground(Color.BLACK);
        updateButton.setForeground(Color.WHITE);
        refreshButton = new JButton("Refresh Bouncers");
        refreshButton.addActionListener(e -> refreshBouncers());
        refreshButton.setBackground(Color.BLACK);
        refreshButton.setForeground(Color.WHITE);
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(updateButton);
        buttonPanel.add(refreshButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        bouncersTable.getSelectionModel().addListSelectionListener(e -> {
            if (bouncersTable.getSelectedRow() != -1) {
                int row = bouncersTable.getSelectedRow();
                selectedBouncerId = Integer.parseInt(model.getValueAt(row, 0).toString());
            }
        });

        add(panel);
        displayBouncers();
    }

    /**
     * Function for displaying all bouncers. Uses a simple GET request to
     * retrieve all bouncer entities in a JSON file. A DocumentBuilder parses
     * the JSON and converts it to a readable string format. Attributes are then
     * parsed as well and added to the table row by row.
     */
    private void displayBouncers() {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            
            // Set basic authentication
            String userCredentials = username + ":" + password;
            String basicAuth = "Basic " + new String(java.util.Base64.getEncoder().encode(userCredentials.getBytes()));
            conn.setRequestProperty("Authorization", basicAuth);

            StringBuilder response;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }

            // Parse the JSON response
            JSONArray bouncersArray = new JSONArray(response.toString());

            DefaultTableModel model = (DefaultTableModel) bouncersTable.getModel();
            model.setRowCount(0);

            for (int i = 0; i < bouncersArray.length(); i++) {
                JSONObject bouncer = bouncersArray.getJSONObject(i);

                int id = bouncer.getInt("id");
                int x = bouncer.getInt("x");
                int y = bouncer.getInt("y");
                int yVelocity = bouncer.getInt("YVelocity");

                model.addRow(new Object[]{id, x, y, yVelocity});
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    /**
     * Update a selected bouncer that has been clicked on by the user. This will
     * prompt three pop-up windows where a user can enter a new X, Y, and yVelocity
     * value for the bouncer. These are then converted to JSON and sent to the
     * bouncer app in the form of a PUT request. A validate function is called
     * on each entry to ensure it is an integer and in a valid range.
     */
    private void updateSelectedBouncer() {
        if (selectedBouncerId != -1) {
            try {
                String newXValue = validateInput("Enter new X value:", "X");
                String newYValue = validateInput("Enter new Y value:", "Y");
                String newYVelocityValue = validateInput("Enter new Y Velocity value:", "Y Velocity");

                if (newXValue != null && newYValue != null && newYVelocityValue != null) {
                    URL url = new URL(urlString + selectedBouncerId);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("PUT");
                    conn.setRequestProperty("Content-Type", "application/json");
                    
                    // Set basic authentication
                    String userCredentials = username + ":" + password;
                    String basicAuth = "Basic " + new String(java.util.Base64.getEncoder().encode(userCredentials.getBytes()));
                    conn.setRequestProperty("Authorization", basicAuth);
                    
                    conn.setDoOutput(true);
                    String jsonInputString = "{\"id\": \"" + selectedBouncerId + "\", \"x\": \"" + newXValue + "\", \"y\": \"" + newYValue + "\", \"YVelocity\": \"" + newYVelocityValue + "\"}";

                    try (OutputStream os = conn.getOutputStream()) {
                        byte[] input = jsonInputString.getBytes("utf-8");
                        os.write(input, 0, input.length);
                    }

                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                        StringBuilder response = new StringBuilder();
                        String line;

                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                    }
                    JOptionPane.showMessageDialog(this, "BOUNCER " + selectedBouncerId + " UPDATED!");
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a bouncer first.");
        }
    }

    /**
     * Function to refresh bouncer info, for example after an update
     */
    private void refreshBouncers() {
        displayBouncers();
    }

    /**
     * Simple validation function that checks if the values being entered for
     * bouncer attributes are integers. Also, in the case of X and Y values,
     * that they fall within the valid range.
     *
     * @param message   value entered by the user for attribute
     * @param attribute attribute being modified, either X, Y, or yVelocity
     * @return input returns the value the user entered if it is valid
     */
    private String validateInput(String message, String attribute) {
        String input;
        boolean validInput = false;
        do {
            input = JOptionPane.showInputDialog(this, message);

            if (input != null) {
                try {
                    int value = Integer.parseInt(input);
                    switch (attribute) {
                        case "X":
                            if (value >= minX && value <= maxX) {
                                validInput = true;
                            } else {
                                JOptionPane.showMessageDialog(this, "X value must be between 0 and 800.");
                            }
                            break;
                        case "Y":
                            if (value >= minY && value <= maxY) {
                                validInput = true;
                            } else {
                                JOptionPane.showMessageDialog(this, "Y value must be between 0 and 600.");
                            }
                            break;
                        case "Y Velocity":
                            validInput = true;
                            break;
                        default:
                            break;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid integer number.");
                }
            } else {
                return null;
            }
        } while (!validInput);

        return input;
    }

    /**
     * Shows the login screen to get the username and password
     */
    private void showLoginScreen() {
        JPanel loginPanel = new JPanel(new GridLayout(3, 2));
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        int loginResult = JOptionPane.showConfirmDialog(this, loginPanel, "Login", JOptionPane.OK_CANCEL_OPTION);

        if (loginResult != JOptionPane.OK_OPTION) {
            // User canceled the login
            System.exit(0);
        }

        // Set the entered credentials
        username = usernameField.getText();
        char[] passwordChars = passwordField.getPassword();
        password = new String(passwordChars);
    }

    /**
     * Main method that starts the application and sets the app to visible
     *
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BouncerInteractionApp app = new BouncerInteractionApp();
            app.setVisible(true);
        });
    }
}
