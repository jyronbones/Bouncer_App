
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Utilities;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

public class BouncerInteractionApp extends JFrame {

    private JTable bouncersTable;
    private JTextArea bouncersTextArea;
    private JButton updateButton;
    private JButton refreshButton;
    private int selectedBouncerId = -1;

    public BouncerInteractionApp() {
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

    private void displayBouncers() {
        try {
            URL url = new URL("http://localhost:8080/bouncer-wray/resources/cst8218.wray.bouncer.entity.bouncer/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(response.toString())));

            NodeList bouncers = doc.getElementsByTagName("bouncer");

            DefaultTableModel model = (DefaultTableModel) bouncersTable.getModel();
            model.setRowCount(0);

            for (int i = 0; i < bouncers.getLength(); i++) {
                Node bouncerNode = bouncers.item(i);
                if (bouncerNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element bouncerElement = (Element) bouncerNode;

                    String id = bouncerElement.getElementsByTagName("id").item(0).getTextContent();
                    String x = bouncerElement.getElementsByTagName("x").item(0).getTextContent();
                    String y = bouncerElement.getElementsByTagName("y").item(0).getTextContent();
                    String yVelocity = bouncerElement.getElementsByTagName("yVelocity").item(0).getTextContent();

                    model.addRow(new Object[]{id, x, y, yVelocity});
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getElementValue(Element parentElement, String elementName) {
        Node node = parentElement.getElementsByTagName(elementName).item(0);
        if (node != null) {
            return node.getTextContent();
        }
        return "";
    }

    private void updateSelectedBouncer() {
        if (selectedBouncerId != -1) {
            try {
                String newXValue = validateInput("Enter new X value:", "X");
                String newYValue = validateInput("Enter new Y value:", "Y");
                String newYVelocityValue = validateInput("Enter new Y Velocity value:", "Y Velocity");

                if (newXValue != null && newYValue != null && newYVelocityValue != null) {
                    URL url = new URL("http://localhost:8080/bouncer-wray/resources/cst8218.wray.bouncer.entity.bouncer/" + selectedBouncerId); // MY ENDPOINT, REPLACE WHEN TESTING WITH YOURS
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("PUT");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setDoOutput(true);

                    String jsonInputString = "{\"id\": \"" + selectedBouncerId + "\", \"x\": \"" + newXValue + "\", \"y\": \"" + newYValue + "\", \"yVelocity\": \"" + newYVelocityValue + "\"}";

                    try (OutputStream os = conn.getOutputStream()) {
                        byte[] input = jsonInputString.getBytes("utf-8");
                        os.write(input, 0, input.length);
                    }

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    // JOptionPane.showMessageDialog(this, "Attributes updated: " + response.toString());
                    JOptionPane.showMessageDialog(this, "BOUNCER " + selectedBouncerId + " UPDATED!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a bouncer first.");
        }
    }

    private void refreshBouncers() {
        displayBouncers();
    }

    private int extractBouncerId(String line) {
        int startIndex = line.indexOf("Bouncer ID: ") + 12;
        int endIndex = line.indexOf(",", startIndex);
        String idStr = line.substring(startIndex, endIndex);
        return Integer.parseInt(idStr.trim());
    }

    private void highlightSelectedBouncer(int start, int end) {
        bouncersTextArea.requestFocusInWindow();
        bouncersTextArea.select(start, end);
    }

    private String validateInput(String message, String attribute) {
        String input;
        boolean validInput = false;
        do {
            input = JOptionPane.showInputDialog(this, message);

            if (input != null) {
                try {
                    int value = Integer.parseInt(input);
                    if (attribute.equals("X")) {
                        if (value >= 0 && value <= 800) {
                            validInput = true;
                        } else {
                            JOptionPane.showMessageDialog(this, "X value must be between 0 and 800.");
                        }
                    } else if (attribute.equals("Y")) {
                        if (value >= 0 && value <= 600) {
                            validInput = true;
                        } else {
                            JOptionPane.showMessageDialog(this, "Y value must be between 0 and 600.");
                        }
                    } else if (attribute.equals("Y Velocity")) {
                        validInput = true;
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BouncerInteractionApp app = new BouncerInteractionApp();
            app.setVisible(true);
        });
    }
}
