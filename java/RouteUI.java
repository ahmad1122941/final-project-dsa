import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class RouteUI extends JFrame {

    JTextField srcField, destField, distField;
    JTextArea outputArea;
    private JPanel mainPanel;

    public RouteUI() {
        // Frame Setup
        setTitle("Route Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 600);
        setLocationRelativeTo(null);
        setResizable(true);

        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(236, 240, 241));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // --- 1. HEADER ---
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(new Color(44, 62, 80));
        headerPanel.setPreferredSize(new Dimension(0, 60));
        headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, new Color(52, 152, 219)));

        JLabel title = new JLabel("🛣️ Route Management System", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(Color.WHITE);

        headerPanel.add(title);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // --- 2. CENTER CONTENT ---
        JPanel centerContent = new JPanel();
        centerContent.setLayout(new BoxLayout(centerContent, BoxLayout.Y_AXIS));
        centerContent.setBackground(new Color(236, 240, 241));
        centerContent.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // FORM CARD
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 223, 228)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        formPanel.setMaximumSize(new Dimension(600, 200));

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 15);
        
        srcField = new JTextField();
        destField = new JTextField();
        distField = new JTextField();

        JTextField[] fields = {srcField, destField, distField};
        for(JTextField f : fields) {
            f.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
        }

        formPanel.add(createLabel("Source City:", labelFont)); formPanel.add(srcField);
        formPanel.add(createLabel("Destination City:", labelFont)); formPanel.add(destField);
        formPanel.add(createLabel("Distance (km):", labelFont)); formPanel.add(distField);

        // BUTTONS PANEL
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        buttonPanel.setMaximumSize(new Dimension(600, 60));
        buttonPanel.setBackground(new Color(236, 240, 241));

        JButton addBtn = new JButton("Add Route");
        JButton deleteBtn = new JButton("Delete Route");
        JButton viewBtn = new JButton("View Routes");

        styleButton(addBtn, new Color(40, 167, 69));
        styleButton(deleteBtn, new Color(220, 53, 69));
        styleButton(viewBtn, new Color(0, 123, 255));

        buttonPanel.add(addBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(viewBtn);

        // OUTPUT CARD
        outputArea = new JTextArea(12, 40);
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        outputArea.setBackground(new Color(245, 245, 245));
        outputArea.setForeground(new Color(50, 50, 50));
        outputArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 223, 228)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        scrollPane.setBackground(Color.WHITE);

        centerContent.add(Box.createVerticalStrut(10));
        centerContent.add(formPanel);
        centerContent.add(Box.createVerticalStrut(15));
        centerContent.add(buttonPanel);
        centerContent.add(Box.createVerticalStrut(15));
        centerContent.add(scrollPane);

        mainPanel.add(centerContent, BorderLayout.CENTER);
        add(mainPanel);

        addBtn.addActionListener(e -> executeAction("ADD"));
        deleteBtn.addActionListener(e -> executeAction("DELETE"));
        viewBtn.addActionListener(e -> executeAction("VIEW"));
    }

    JLabel createLabel(String text, Font font) {
        JLabel l = new JLabel(text, JLabel.RIGHT);
        l.setFont(font);
        l.setForeground(new Color(60, 60, 60));
        return l;
    }

    void styleButton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { btn.setBackground(color.brighter()); }
            public void mouseExited(java.awt.event.MouseEvent evt) { btn.setBackground(color); }
        });
    }

    // ✅ NEW LOGIC: Java based File I/O (No C++ needed for this to work now)
    private void executeAction(String command) {
        File dataDir = new File("../data");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }

        File file = new File("../data/routes.txt");
        java.util.List<String> routesList = new ArrayList<>();

        // 1. Read existing data from file
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        routesList.add(line);
                    }
                }
            } catch (IOException ex) {
                outputArea.setForeground(Color.RED);
                outputArea.setText("Error reading file: " + ex.getMessage());
                return;
            }
        }

        try {
            if (command.equals("ADD")) {
                String src = srcField.getText().trim();
                String dest = destField.getText().trim();
                String dist = distField.getText().trim();

                if(src.isEmpty() || dest.isEmpty() || dist.isEmpty()) {
                    outputArea.setForeground(Color.RED);
                    outputArea.setText("Please fill all fields!");
                    return;
                }

                String newRoute = src + " " + dest + " " + dist;
                routesList.add(newRoute); // Add new route to list
                
                // Save to file
                saveToFile(routesList);
                
                outputArea.setForeground(new Color(46, 204, 113));
                outputArea.setText("Route Added Successfully:\n" + newRoute + "\n");
                
                // Clear fields
                srcField.setText("");
                destField.setText("");
                distField.setText("");

            } else if (command.equals("DELETE")) {
                String src = srcField.getText().trim();
                String dest = destField.getText().trim();
                
                if(src.isEmpty() || dest.isEmpty()) {
                    outputArea.setForeground(Color.RED);
                    outputArea.setText("Enter Source and Destination to delete.");
                    return;
                }

                boolean removed = false;
                Iterator<String> it = routesList.iterator();
                while (it.hasNext()) {
                    String line = it.next();
                    // Check if line starts with "Source Dest"
                    if (line.startsWith(src + " " + dest)) {
                        it.remove();
                        removed = true;
                        break;
                    }
                }

                saveToFile(routesList);

                if (removed) {
                    outputArea.setForeground(new Color(231, 76, 60));
                    outputArea.setText("Route deleted successfully.");
                } else {
                    outputArea.setForeground(Color.RED);
                    outputArea.setText("Route not found!");
                }

            } else if (command.equals("VIEW")) {
                StringBuilder sb = new StringBuilder();
                sb.append("--- Available Routes ---\n");
                if (routesList.isEmpty()) {
                    sb.append("(No routes found. Please add some.)");
                } else {
                    for (String route : routesList) {
                        sb.append(route).append("\n");
                    }
                }
                outputArea.setForeground(Color.BLACK);
                outputArea.setText(sb.toString());
            }

        } catch (Exception ex) {
            outputArea.setForeground(Color.RED);
            outputArea.setText("Error: " + ex.getMessage());
        }
    }

    // Helper method to write list to file
    private void saveToFile(java.util.List<String> list) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("../data/routes.txt"))) {
            for (String line : list) {
                bw.write(line);
                bw.newLine();
            }
        }
    }

    public JPanel getMainPanel() { return mainPanel; }

    public static void run() {
        SwingUtilities.invokeLater(() -> new RouteUI().setVisible(true));
    }

    public static void main(String[] args) {
        run();
    }
}