import java.awt.*;
import java.io.*;
import javax.swing.*;

// ✅ Class ab JFrame extend karti hai
public class FareUI extends JFrame {

    JComboBox<String> vehicle;
    JTextField distance;
    JTextArea output;
    private JPanel mainPanel;

    public FareUI() {
        // Frame Setup (Stable Window)
        setTitle("Dynamic Fare Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 500); // ✅ Compact & Stable size
        setLocationRelativeTo(null); // Center on screen
        setResizable(true);

        // Main Panel Layout
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(236, 240, 241)); // Modern Gray Background
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // --- 1. HEADER ---
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(new Color(44, 62, 80)); // Dark Blue
        headerPanel.setPreferredSize(new Dimension(0, 60));
        headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, new Color(52, 152, 219)));

        JLabel title = new JLabel("Dynamic Fare Calculator", JLabel.CENTER);
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
        JPanel form = new JPanel(new GridLayout(2, 2, 10, 10));
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 223, 228)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        // Form size restrict
        form.setMaximumSize(new Dimension(500, 150));

        vehicle = new JComboBox<>(new String[]{"Bike", "Car", "Bus"});
        vehicle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        vehicle.setBackground(Color.WHITE);
        distance = new JTextField();
        
        // Text Field Styling
        distance.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        distance.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        form.add(createLabel("Vehicle Type"));
        form.add(vehicle);
        form.add(createLabel("Distance (km)"));
        form.add(distance);

        // BUTTON PANEL
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(236, 240, 241));
        
        JButton calc = new JButton("Calculate Fare");
        styleButton(calc, new Color(39, 174, 96)); // Green
        btnPanel.add(calc);

        // OUTPUT CARD (Terminal Style)
        output = new JTextArea(8, 40);
        output.setEditable(false);
        output.setBackground(Color.BLACK); // Terminal look
        output.setForeground(Color.GREEN);
        output.setFont(new Font("Consolas", Font.PLAIN, 14));
        output.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scroll = new JScrollPane(output);
        scroll.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 223, 228)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        // Add to Center Content
        centerContent.add(form);
        centerContent.add(Box.createVerticalStrut(15));
        centerContent.add(btnPanel);
        centerContent.add(Box.createVerticalStrut(15));
        centerContent.add(scroll);

        mainPanel.add(centerContent, BorderLayout.CENTER);
        add(mainPanel);

        calc.addActionListener(e -> calculate());
    }

    // Helper: Label
    JLabel createLabel(String text) {
        JLabel l = new JLabel(text, JLabel.RIGHT);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        l.setForeground(new Color(60, 60, 60));
        return l;
    }

    // Helper: Button Style
    void styleButton(JButton b, Color c) {
        b.setBackground(c);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(new Font("Segoe UI", Font.BOLD, 15));
        b.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { b.setBackground(c.brighter()); }
            public void mouseExited(java.awt.event.MouseEvent evt) { b.setBackground(c); }
        });
    }

    // Logic: Connect to C++ Backend
    void calculate() {
        try {
            // ✅ Path Fix: Check directory
            File dir = new File("../data");
            if (!dir.exists()) dir.mkdirs();

            // ✅ Path Fix: Writing to ../data
            FileWriter fw = new FileWriter("../data/fare_input.txt");
            fw.write(vehicle.getSelectedItem() + " " + distance.getText());
            fw.close();

            output.setForeground(Color.YELLOW);
            output.append("Calculating...\n");

            // ✅ Path Fix: Running exe from ../cpp
            ProcessBuilder pb = new ProcessBuilder("../cpp/fare.exe");
            pb.directory(new File(System.getProperty("user.dir")));
            Process p = pb.start();
            p.waitFor();

            // ✅ Path Fix: Reading from ../data
            File outFile = new File("../data/fare_output.txt");
            if(outFile.exists()){
                BufferedReader br = new BufferedReader(new FileReader(outFile));
                output.setText(""); // Clear previous
                output.setForeground(Color.GREEN);
                String line;
                while ((line = br.readLine()) != null) {
                    output.append(line + "\n");
                }
                br.close();
            } else {
                output.setForeground(Color.RED);
                output.setText("Error: Output file not found.");
            }

        } catch (Exception ex) {
            output.setForeground(Color.RED);
            output.setText("ERROR: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // Get main panel for embedding
    public JPanel getMainPanel() { return mainPanel; }

    // ✅ STATIC RUN METHOD (Jaisa aapne manga)
    public static void run() {
        SwingUtilities.invokeLater(() -> new FareUI().setVisible(true));
    }

    public static void main(String[] args) {
        run();
    }
}