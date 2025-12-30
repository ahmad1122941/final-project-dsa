import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class VehicleUI {

    private JTextField idField, typeField, capacityField;
    private JTextArea outputArea;
    private JPanel mainPanel;

    public VehicleUI() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(230, 240, 255));

        // Title Setup
        JLabel title = new JLabel("Vehicle Management System", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(new Color(25, 25, 112));
        title.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        mainPanel.add(title, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 12, 12));
        formPanel.setBorder(BorderFactory.createTitledBorder("Vehicle Details"));
        formPanel.setBackground(Color.WHITE);
        
        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        
        idField = new JTextField();
        typeField = new JTextField();
        capacityField = new JTextField();

        formPanel.add(createLabel("Vehicle ID:", labelFont));
        formPanel.add(idField);
        formPanel.add(createLabel("Vehicle Type:", labelFont));
        formPanel.add(typeField);
        formPanel.add(createLabel("Capacity:", labelFont));
        formPanel.add(capacityField);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(230, 240, 255));
        
        JButton addBtn = new JButton("Add Vehicle");
        JButton searchBtn = new JButton("Search Vehicle");
        JButton deleteBtn = new JButton("Delete Vehicle");

        styleButton(addBtn, new Color(40, 167, 69));
        styleButton(searchBtn, new Color(0, 123, 255));
        styleButton(deleteBtn, new Color(220, 53, 69));

        addHoverEffect(addBtn, new Color(40, 200, 80));
        addHoverEffect(searchBtn, new Color(0, 150, 255));
        addHoverEffect(deleteBtn, new Color(255, 70, 70));

        buttonPanel.add(addBtn);
        buttonPanel.add(searchBtn);
        buttonPanel.add(deleteBtn);

        // Output Area
        outputArea = new JTextArea(8, 40);
        outputArea.setEditable(false);
        outputArea.setBackground(new Color(30, 30, 30));
        outputArea.setForeground(Color.GREEN);
        outputArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        outputArea.setBorder(BorderFactory.createTitledBorder("Output"));
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // Center Layout
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        centerPanel.setBackground(new Color(230, 240, 255));
        centerPanel.add(formPanel, BorderLayout.NORTH);
        centerPanel.add(buttonPanel, BorderLayout.CENTER);
        centerPanel.add(scrollPane, BorderLayout.SOUTH);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Action Listeners
        addBtn.addActionListener(e -> runCommand("ADD"));
        searchBtn.addActionListener(e -> runCommand("SEARCH"));
        deleteBtn.addActionListener(e -> runCommand("DELETE"));
    }

    private JLabel createLabel(String text, Font font) {
        JLabel l = new JLabel(text);
        l.setFont(font);
        return l;
    }

    private void styleButton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
    }

    private void addHoverEffect(JButton btn, Color hoverColor) {
        Color original = btn.getBackground();
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) { btn.setBackground(hoverColor); }
            public void mouseExited(MouseEvent evt) { btn.setBackground(original); }
        });
    }

    private void runCommand(String command) {
        try {
            // 1. Ensure Data folder exists
            File inputDir = new File("data");
            if (!inputDir.exists()) {
                inputDir.mkdirs(); 
            }
            
            // 2. Write to input file
            FileWriter fw = new FileWriter("data/input.txt");
            switch(command) {
                case "ADD" -> fw.write("ADD " + idField.getText() + " " + typeField.getText() + " " + capacityField.getText());
                case "SEARCH" -> fw.write("SEARCH " + idField.getText());
                case "DELETE" -> fw.write("DELETE " + idField.getText());
            }
            fw.close();

            outputArea.setForeground(Color.YELLOW);
            outputArea.setText("Processing command...");
            
            // 3. Define Exe Path (Fixed: "../" goes up one level from 'java' folder)
            String exePath = "../cpp/vehicle.exe";
            
            // Check if EXE exists before running
            File exeFile = new File(exePath);
            if (!exeFile.exists()) {
                throw new IOException("vehicle.exe NOT FOUND!\nLocation tried: " + exeFile.getAbsolutePath() + 
                                      "\n\nPlease ensure your 'cpp' folder is OUTSIDE the 'java' folder and you have compiled your C++ code.");
            }

            // 4. Run C++ Backend
            ProcessBuilder pb = new ProcessBuilder(exePath);
            pb.directory(new File(System.getProperty("user.dir"))); // Set CWD to 'java' folder
            Process p = pb.start();
            p.waitFor(); 

            // 5. Read Output
            File outputFile = new File("data/output.txt");
            if(outputFile.exists()){
                BufferedReader br = new BufferedReader(new FileReader(outputFile));
                outputArea.setForeground(Color.GREEN);
                outputArea.setText("");
                String line;
                while ((line = br.readLine()) != null) {
                    outputArea.append(line + "\n");
                }
                br.close();
            } else {
                outputArea.setForeground(Color.RED);
                outputArea.setText("Error: data/output.txt not found after running backend.");
            }

        } catch (Exception ex) {
            outputArea.setForeground(Color.RED);
            outputArea.setText("ERROR: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public JPanel getMainPanel() { return mainPanel; }

    public static void run() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Vehicle Management");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 500);
            frame.add(new VehicleUI().getMainPanel());
            frame.setVisible(true);
        });
    }
}