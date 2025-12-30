import java.awt.*;
import java.io.*;
import javax.swing.*;

// ✅ Class ab JFrame extend karti hai
public class ReportUI extends JFrame {

    private JTextArea output;
    private JPanel mainPanel;

    public ReportUI() {
        // Frame Setup
        setTitle("System Analytics & Reports");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 650);
        setLocationRelativeTo(null);
        setResizable(true);

        // Main Panel Layout
        mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(new Color(236, 240, 241));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- 1. HEADER ---
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(new Color(44, 62, 80));
        headerPanel.setPreferredSize(new Dimension(0, 60));
        headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, new Color(52, 152, 219)));

        JLabel title = new JLabel("📈 System Analytics & Report", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(Color.WHITE);

        headerPanel.add(title);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // --- 2. CENTER (Button) ---
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(236, 240, 241));
        
        JButton generate = new JButton("Generate Full System Report");
        styleButton(generate, new Color(0, 123, 255), new Color(0, 150, 255));
        btnPanel.add(generate);

        // --- 3. OUTPUT AREA (Terminal Style) ---
        output = new JTextArea();
        output.setEditable(false);
        output.setFont(new Font("Consolas", Font.PLAIN, 13));
        output.setBackground(new Color(30, 30, 30));
        output.setForeground(new Color(0, 255, 128)); // Terminal Green
        output.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JScrollPane scroll = new JScrollPane(output);
        scroll.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 223, 228)),
            BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));

        // Assemble
        mainPanel.add(btnPanel, BorderLayout.NORTH);
        mainPanel.add(scroll, BorderLayout.CENTER);
        add(mainPanel);

        generate.addActionListener(e -> generateReport());
    }

    // Helper: Button Styling
    private void styleButton(JButton btn, Color normal, Color hover) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setBackground(normal);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { btn.setBackground(hover); }
            public void mouseExited(java.awt.event.MouseEvent evt) { btn.setBackground(normal); }
        });
    }

    // ✅ NEW LOGIC: Data aggregation from other modules
    private void generateReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("===================================================\n");
        sb.append("          SYSTEM ANALYTICS & DATA REPORT             \n");
        sb.append("===================================================\n\n");

        // 1. Read Vehicles (From VehicleUI -> input.txt)
        File vehicleFile = new File("../data/input.txt");
        sb.append(">>> 1. REGISTERED VEHICLES:\n");
        if(vehicleFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(vehicleFile))) {
                String line;
                boolean hasData = false;
                while ((line = br.readLine()) != null) {
                    if(!line.trim().isEmpty()) {
                        sb.append(line).append("\n");
                        hasData = true;
                    }
                }
                if(!hasData) sb.append("   (No vehicle data found)\n");
            } catch (Exception e) {
                sb.append("   Error reading vehicle data: " + e.getMessage() + "\n");
            }
        } else {
            sb.append("   (No vehicle file found)\n");
        }

        sb.append("\n");

        // 2. Read Routes (From RouteUI -> routes.txt)
        File routeFile = new File("../data/routes.txt");
        sb.append(">>> 2. REGISTERED ROUTES:\n");
        if(routeFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(routeFile))) {
                String line;
                boolean hasData = false;
                while ((line = br.readLine()) != null) {
                    if(!line.trim().isEmpty()) {
                        sb.append(line).append("\n");
                        hasData = true;
                    }
                }
                if(!hasData) sb.append("   (No route data found)\n");
            } catch (Exception e) {
                sb.append("   Error reading route data: " + e.getMessage() + "\n");
            }
        } else {
            sb.append("   (No route file found)\n");
        }

        sb.append("\n");

        // 3. Read Schedules/Bookings (From SchedulerUI -> scheduler_input.txt)
        File bookingFile = new File("../data/scheduler_input.txt");
        sb.append(">>> 3. BOOKING & SCHEDULES:\n");
        if(bookingFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(bookingFile))) {
                String line;
                boolean hasData = false;
                while ((line = br.readLine()) != null) {
                    if(!line.trim().isEmpty()) {
                        sb.append(line).append("\n");
                        hasData = true;
                    }
                }
                if(!hasData) sb.append("   (No booking data found)\n");
            } catch (Exception e) {
                sb.append("   Error reading booking data: " + e.getMessage() + "\n");
            }
        } else {
            sb.append("   (No booking file found)\n");
        }

        sb.append("\n===================================================\n");
        sb.append("                  END OF REPORT                      \n");
        sb.append("===================================================\n");

        output.setText(sb.toString());
    }

    // Get main panel for embedding
    public JPanel getMainPanel() { return mainPanel; }

    // ✅ STATIC RUN METHOD (Jaisa aapne manga)
    public static void run() {
        SwingUtilities.invokeLater(() -> new ReportUI().setVisible(true));
    }

    public static void main(String[] args) {
        run();
    }
}