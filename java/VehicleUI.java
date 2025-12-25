import java.awt.*;
import java.io.*;
import javax.swing.*;

public class VehicleUI {

    JTextField idField, typeField, capacityField;
    JTextArea outputArea;

    public VehicleUI() {
        JFrame frame = new JFrame("Smart Transport Management System");
        frame.setSize(750, 520);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // ===== Main Panel =====
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(230, 240, 255));

        // ===== Title =====
        JLabel title = new JLabel("🚗 Vehicle Management System", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(new Color(25, 25, 112));
        title.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        mainPanel.add(title, BorderLayout.NORTH);

        // ===== Form Panel =====
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 12, 12));
        formPanel.setBorder(BorderFactory.createTitledBorder("Vehicle Details"));
        formPanel.setBackground(Color.WHITE);

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);

        JLabel l1 = new JLabel("Vehicle ID:");
        l1.setFont(labelFont);
        formPanel.add(l1);
        idField = new JTextField();
        formPanel.add(idField);

        JLabel l2 = new JLabel("Vehicle Type:");
        l2.setFont(labelFont);
        formPanel.add(l2);
        typeField = new JTextField();
        formPanel.add(typeField);

        JLabel l3 = new JLabel("Capacity:");
        l3.setFont(labelFont);
        formPanel.add(l3);
        capacityField = new JTextField();
        formPanel.add(capacityField);

        // ===== Button Panel =====
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(230, 240, 255));

        JButton addBtn = new JButton("Add Vehicle");
        JButton searchBtn = new JButton("Search Vehicle");
        JButton deleteBtn = new JButton("Delete Vehicle");

        styleButton(addBtn, new Color(40, 167, 69));
        styleButton(searchBtn, new Color(0, 123, 255));
        styleButton(deleteBtn, new Color(220, 53, 69));

        buttonPanel.add(addBtn);
        buttonPanel.add(searchBtn);
        buttonPanel.add(deleteBtn);

        // ===== Output Area =====
        outputArea = new JTextArea(8, 40);
        outputArea.setEditable(false);
        outputArea.setBackground(new Color(30, 30, 30));
        outputArea.setForeground(Color.GREEN);
        outputArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        outputArea.setBorder(BorderFactory.createTitledBorder("Output"));

        JScrollPane scrollPane = new JScrollPane(outputArea);

        // ===== Center Panel =====
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        centerPanel.setBackground(new Color(230, 240, 255));

        centerPanel.add(formPanel, BorderLayout.NORTH);
        centerPanel.add(buttonPanel, BorderLayout.CENTER);
        centerPanel.add(scrollPane, BorderLayout.SOUTH);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        frame.add(mainPanel);

        // ===== Button Actions =====
        addBtn.addActionListener(e -> runCommand("ADD"));
        searchBtn.addActionListener(e -> runCommand("SEARCH"));
        deleteBtn.addActionListener(e -> runCommand("DELETE"));

        frame.setVisible(true);
    }

    // ===== Button Styling Method =====
    void styleButton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
    }

    // ===== Integration Logic =====
    void runCommand(String command) {
        try {
            FileWriter fw = new FileWriter("data/input.txt");

            if (command.equals("ADD")) {
                fw.write("ADD " + idField.getText() + " " +
                         typeField.getText() + " " +
                         capacityField.getText());
            } else if (command.equals("SEARCH")) {
                fw.write("SEARCH " + idField.getText());
            } else if (command.equals("DELETE")) {
                fw.write("DELETE " + idField.getText());
            }

            fw.close();

            ProcessBuilder pb = new ProcessBuilder("cpp/vehicle.exe");
            pb.start().waitFor();

            BufferedReader br = new BufferedReader(new FileReader("data/output.txt"));
            outputArea.setText("");
            String line;
            while ((line = br.readLine()) != null) {
                outputArea.append(line + "\n");
            }
            br.close();

        } catch (Exception ex) {
            outputArea.setText("ERROR: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new VehicleUI();
    }
}
