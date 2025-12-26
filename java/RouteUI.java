import java.awt.*;
import java.io.*;
import javax.swing.*;

public class RouteUI {

    JTextField srcField, destField, distField;
    JTextArea outputArea;

    public RouteUI() {
        JFrame frame = new JFrame("Smart Transport Management System - Route Module");
        frame.setSize(750, 520);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // ===== Main Panel =====
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));

        // ===== Title =====
        JLabel title = new JLabel("🛣️ Route Management System", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(new Color(25, 25, 112));
        title.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        mainPanel.add(title, BorderLayout.NORTH);

        // ===== Form Panel =====
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 12, 12));
        formPanel.setBorder(BorderFactory.createTitledBorder("Route Details"));
        formPanel.setBackground(Color.WHITE);

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);

        JLabel l1 = new JLabel("Source City:");
        l1.setFont(labelFont);
        formPanel.add(l1);
        srcField = new JTextField();
        formPanel.add(srcField);

        JLabel l2 = new JLabel("Destination City:");
        l2.setFont(labelFont);
        formPanel.add(l2);
        destField = new JTextField();
        formPanel.add(destField);

        JLabel l3 = new JLabel("Distance (km):");
        l3.setFont(labelFont);
        formPanel.add(l3);
        distField = new JTextField();
        formPanel.add(distField);

        // ===== Button Panel =====
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 245, 245));

        JButton addBtn = new JButton("Add Route");
        JButton deleteBtn = new JButton("Delete Route");
        JButton viewBtn = new JButton("View Routes");

        styleButton(addBtn, new Color(40, 167, 69));
        styleButton(deleteBtn, new Color(220, 53, 69));
        styleButton(viewBtn, new Color(0, 123, 255));

        buttonPanel.add(addBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(viewBtn);

        // ===== Output Area =====
        outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);
        outputArea.setBackground(new Color(30, 30, 30));
        outputArea.setForeground(Color.GREEN);
        outputArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        outputArea.setBorder(BorderFactory.createTitledBorder("Routes Output"));

        JScrollPane scrollPane = new JScrollPane(outputArea);

        // ===== Center Panel =====
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        centerPanel.setBackground(new Color(245, 245, 245));

        centerPanel.add(formPanel, BorderLayout.NORTH);
        centerPanel.add(buttonPanel, BorderLayout.CENTER);
        centerPanel.add(scrollPane, BorderLayout.SOUTH);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        frame.add(mainPanel);

        // ===== Button Actions =====
        addBtn.addActionListener(e -> runCommand("ADD"));
        deleteBtn.addActionListener(e -> runCommand("DELETE"));
        viewBtn.addActionListener(e -> runCommand("VIEW"));

        frame.setVisible(true);
    }

    // ===== Button Styling =====
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

            if(command.equals("ADD")) {
                fw.write("ADD " + srcField.getText() + " " +
                         destField.getText() + " " +
                         distField.getText());
            } else if(command.equals("DELETE")) {
                fw.write("DELETE " + srcField.getText() + " " +
                         destField.getText());
            } else if(command.equals("VIEW")) {
                fw.write("VIEW");
            }

            fw.close();

            ProcessBuilder pb = new ProcessBuilder("cpp/route.exe");
            pb.start().waitFor();

            BufferedReader br = new BufferedReader(new FileReader("data/routes.txt"));
            outputArea.setText("");
            String line;
            while((line = br.readLine()) != null) {
                outputArea.append(line + "\n");
            }
            br.close();

        } catch(Exception ex) {
            outputArea.setText("ERROR: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new RouteUI();
    }
}
