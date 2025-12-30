import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class SchedulerUI extends JFrame {

    JTextField bookingId, customer;
    JComboBox<String> srcCombo, destCombo;
    JTextArea output;
    private JPanel mainPanel;

    // ✅ Added City List for More Options
    private String[] cityNames = {
        "Lahore","Karachi","Islamabad","Peshawar","Quetta","Multan",
        "Faisalabad","Rawalpindi","Sialkot","Gujranwala","Sargodha",
        "Hyderabad","Sukkur","Larkana","Bahawalpur","D.G. Khan",
        "Mardan","Mingora","Abbottabad","Haripur","Mansehra",
        "Chakwal","Jhelum","Gujrat","Sahiwal","Okara","Kasur",
        "Sheikhupura","Jhang","Mianwali","Bannu","Kohat","D.I. Khan",
        "Rahim Yar Khan","Sadiqabad","Jacobabad","Khuzdar","Turbat","Gwadar",
        "Panjgur","Zhob","Loralai","Sibi","Nawabshah","Mirpur Khas","Tando Adam",
        "Chiniot","Khanewal","Vehari","Nowshera","Swabi"
    };

    public SchedulerUI() {
        // Frame Setup
        setTitle("Vehicle Scheduling System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 650); 
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Modern Background
        mainPanel = new JPanel(new BorderLayout(0, 0));
        mainPanel.setBackground(new Color(234, 237, 242)); // Soft Gray Blue

        // --- HEADER ---
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(44, 62, 80));
        header.setPreferredSize(new Dimension(0, 70));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, new Color(52, 152, 219)));

        JLabel title = new JLabel(" Vehicle Scheduler", JLabel.LEFT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(Color.WHITE);
        title.setBorder(new EmptyBorder(0, 20, 0, 0));
        
        JLabel subtitle = new JLabel("Smart Transport Management", JLabel.RIGHT);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(new Color(189, 195, 199));
        subtitle.setBorder(new EmptyBorder(0, 0, 0, 20));

        header.add(title, BorderLayout.WEST);
        header.add(subtitle, BorderLayout.EAST);

        // --- CONTENT AREA (Split: Inputs | Output) ---
        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        contentPanel.setBackground(new Color(234, 237, 242));
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // 1. LEFT PANEL: INPUT FORM
        JPanel inputCard = new JPanel();
        inputCard.setLayout(new BoxLayout(inputCard, BoxLayout.Y_AXIS));
        inputCard.setBackground(Color.WHITE);
        inputCard.setBorder(BorderFactory.createLineBorder(new Color(220, 223, 228)));

        // Title inside card
        JLabel inputTitle = new JLabel("New Booking Request");
        inputTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        inputTitle.setForeground(new Color(52, 73, 94));
        inputTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputTitle.setBorder(new EmptyBorder(20, 0, 20, 0));

        inputCard.add(inputTitle);

        // Form Grid
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 15));
        formPanel.setBackground(Color.WHITE);
        formPanel.setMaximumSize(new Dimension(400, 250));
        formPanel.setBorder(new EmptyBorder(0, 20, 20, 20));

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);

        // Fields
        bookingId = new JTextField();
        customer = new JTextField();
        srcCombo = new JComboBox<>(cityNames);
        destCombo = new JComboBox<>(cityNames);

        // Styling Inputs
        styleField(bookingId);
        styleField(customer);
        styleCombo(srcCombo);
        styleCombo(destCombo);

        formPanel.add(createLabel("Booking ID:", labelFont)); formPanel.add(bookingId);
        formPanel.add(createLabel("Customer Name:", labelFont)); formPanel.add(customer);
        formPanel.add(createLabel("Source City:", labelFont)); formPanel.add(srcCombo);
        formPanel.add(createLabel("Destination City:", labelFont)); formPanel.add(destCombo);

        inputCard.add(formPanel);

        // Buttons
        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        btnPanel.setMaximumSize(new Dimension(350, 50));
        btnPanel.setBackground(Color.WHITE);
        btnPanel.setBorder(new EmptyBorder(0, 20, 30, 20));

        JButton assignBtn = createButton("Assign Vehicle", new Color(46, 204, 113));
        JButton viewBtn = createButton("View Schedule", new Color(52, 152, 219));

        btnPanel.add(assignBtn);
        btnPanel.add(viewBtn);

        inputCard.add(btnPanel);

        // 2. RIGHT PANEL: OUTPUT (TERMINAL STYLE)
        JPanel outputCard = new JPanel(new BorderLayout());
        outputCard.setBackground(new Color(44, 62, 80)); // Dark Background
        outputCard.setBorder(BorderFactory.createLineBorder(new Color(52, 152, 219)));

        JLabel outTitle = new JLabel(" System Logs / Output ");
        outTitle.setFont(new Font("Consolas", Font.BOLD, 14));
        outTitle.setForeground(new Color(189, 195, 199));
        outTitle.setBorder(new EmptyBorder(10, 10, 5, 0));

        output = new JTextArea();
        output.setEditable(false);
        output.setFont(new Font("Consolas", Font.PLAIN, 13));
        output.setBackground(new Color(44, 62, 80));
        output.setForeground(new Color(46, 204, 113)); // Terminal Green
        output.setBorder(null);

        JScrollPane scroll = new JScrollPane(output);
        scroll.setBorder(null);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        // Custom Scrollbar color
        scroll.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override protected void configureScrollBarColors() {
                this.thumbColor = new Color(52, 152, 219);
                this.trackColor = new Color(44, 62, 80);
            }
        });

        outputCard.add(outTitle, BorderLayout.NORTH);
        outputCard.add(scroll, BorderLayout.CENTER);

        // Add to content
        contentPanel.add(inputCard);
        contentPanel.add(outputCard);

        // Add to main
        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        add(mainPanel);

        // Actions
        assignBtn.addActionListener(e -> execute("ADD"));
        viewBtn.addActionListener(e -> execute("VIEW"));
    }

    // --- HELPER METHODS FOR STYLING ---

    JLabel createLabel(String text, Font font) {
        JLabel l = new JLabel(text, JLabel.RIGHT);
        l.setFont(font);
        l.setForeground(new Color(127, 140, 141));
        return l;
    }

    void styleField(JTextField tf) {
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199)),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        tf.setBackground(new Color(250, 250, 250));
    }

    void styleCombo(JComboBox<String> cb) {
        cb.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cb.setBackground(Color.WHITE);
        cb.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199)),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
    }

    JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover Effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(color.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(color);
            }
        });
        return btn;
    }

    // --- LOGIC ---

    void execute(String cmd) {
        try {
            File dir = new File("../data");
            if (!dir.exists()) dir.mkdirs();

            if (cmd.equals("ADD")) {
                // Validations
                if(bookingId.getText().isEmpty() || customer.getText().isEmpty()) {
                    output.append(">> ERROR: ID and Name are required.\n");
                    return;
                }

                // ✅ CHANGED: Getting data from ComboBox
                String src = srcCombo.getSelectedItem().toString();
                String dest = destCombo.getSelectedItem().toString();

                FileWriter fw = new FileWriter("../data/scheduler_input.txt", true);
                fw.write(bookingId.getText() + " " + customer.getText() + " " + src + " " + dest + "\n");
                fw.close();
                
                output.append(">> Data Sent to Backend...\n");
                output.append(">> Booking ID: " + bookingId.getText() + " Added.\n");
            }

            // Run C++ Backend
            ProcessBuilder pb = new ProcessBuilder("../cpp/scheduler.exe");
            pb.directory(new File(System.getProperty("user.dir")));
            Process p = pb.start();
            p.waitFor();

            File outFile = new File("../data/scheduler_output.txt");
            if(outFile.exists()){
                BufferedReader br = new BufferedReader(new FileReader(outFile));
                
                if(cmd.equals("VIEW")) {
                    output.setText(">> Retrieving Schedule...\n");
                    output.setForeground(new Color(46, 204, 113)); // Reset to green
                }

                String line;
                // Read output file
                while ((line = br.readLine()) != null) output.append("   " + line + "\n");
                br.close();
            }

        } catch (Exception ex) {
            output.setForeground(Color.RED);
            output.append(">> SYSTEM ERROR: " + ex.getMessage() + "\n");
            ex.printStackTrace();
        }
    }

    public JPanel getMainPanel() { return mainPanel; }

    public static void run() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new SchedulerUI().setVisible(true));
    }

    public static void main(String[] args) {
        run();
    }
}