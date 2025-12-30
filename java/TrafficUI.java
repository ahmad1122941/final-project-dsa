import java.awt.*;
import java.io.*;
import javax.swing.*;

// ✅ Class ab JFrame extend karti hai taaki setVisible(true) method kaam kare
public class TrafficUI extends JFrame {

    private JComboBox<String> srcCombo, destCombo;
    private JTextArea output;
    private JPanel mainPanel;

    private final String[] cities = {
        "Islamabad","Lahore","Multan","Peshawar","Faisalabad","Bahawalpur","Mardan",
        "Swat","Sargodha","RahimYarKhan","Swabi","Quetta","Gwadar","Karachi","Hyderabad",
        "Sukkur","Larkana","Okara","Sahiwal","Bahawalnagar","DeraGhaziKhan",
        "MirpurKhas","Jacobabad","Khuzdar","Kandahar"
    };

    public TrafficUI() {
        // Frame Setup (Window ki properties)
        setTitle("Traffic-Aware Route Planning System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500); // Window ka size
        setLocationRelativeTo(null); // Screen ke center mein

        // Main Panel Setup
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 250));

        JLabel title = new JLabel("Traffic-Aware Shortest Path", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(new Color(25, 25, 112));
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        mainPanel.add(title, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        formPanel.setBorder(BorderFactory.createTitledBorder("Select Cities"));
        formPanel.setBackground(Color.WHITE);

        srcCombo = new JComboBox<>(cities);
        destCombo = new JComboBox<>(cities);

        formPanel.add(new JLabel("Source City:", JLabel.RIGHT));
        formPanel.add(srcCombo);
        formPanel.add(new JLabel("Destination City:", JLabel.RIGHT));
        formPanel.add(destCombo);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 245, 250));
        JButton checkBtn = new JButton("Check Route");
        styleButton(checkBtn, new Color(0, 123, 255));
        buttonPanel.add(checkBtn);

        // Output area
        output = new JTextArea(10, 60);
        output.setEditable(false);
        output.setBackground(new Color(30, 30, 30));
        output.setForeground(Color.GREEN);
        output.setFont(new Font("Consolas", Font.PLAIN, 14));
        output.setBorder(BorderFactory.createTitledBorder("Output"));

        JScrollPane scroll = new JScrollPane(output);

        JPanel centerPanel = new JPanel(new BorderLayout(15, 15));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        centerPanel.setBackground(new Color(245, 245, 250));

        centerPanel.add(formPanel, BorderLayout.NORTH);
        centerPanel.add(buttonPanel, BorderLayout.CENTER);
        centerPanel.add(scroll, BorderLayout.SOUTH);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // ✅ Main panel ko frame par add kiya
        add(mainPanel);

        checkBtn.addActionListener(e -> runTraffic());
    }

    private void styleButton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    private void runTraffic() {
        try {
            // Data folder check karein agar nahi hai to bana dein
            File dir = new File("../data");
            if(!dir.exists()) dir.mkdirs();

            FileWriter fw = new FileWriter("../data/traffic_input.txt");
            fw.write(srcCombo.getSelectedItem() + " " + destCombo.getSelectedItem());
            fw.close();

            // C++ Backend call
            ProcessBuilder pb = new ProcessBuilder("../cpp/traffic.exe");
            Process p = pb.start();
            p.waitFor();

            BufferedReader br = new BufferedReader(new FileReader("../data/traffic_output.txt"));
            output.setText("");
            String line;
            while ((line = br.readLine()) != null) output.append(line + "\n");
            br.close();

        } catch (Exception ex) {
            output.setForeground(Color.RED);
            output.setText("ERROR: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // Get main panel for dashboard integration (if needed in future)
    public JPanel getMainPanel() { return mainPanel; }

    // ✅ STATIC RUN METHOD (Jaisa aapne manga)
    public static void run() {
        SwingUtilities.invokeLater(() -> new TrafficUI().setVisible(true));
    }

    public static void main(String[] args) {
        // Directly run karne ke liye
        run();
    }
}