import java.awt.*;
import java.io.*;
import javax.swing.*;

public class BookingUI {

    JTextField id, name, src, dest;
    JTextArea output;

    public BookingUI() {
        JFrame frame = new JFrame("🚕 Booking Management System");
        frame.setSize(750, 520);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(230, 240, 255));

        JLabel title = new JLabel("Ride Booking Module", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(new Color(25, 25, 112));
        title.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        mainPanel.add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 12, 12));
        formPanel.setBorder(BorderFactory.createTitledBorder("Booking Details"));
        formPanel.setBackground(Color.WHITE);

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);

        id = new JTextField();
        name = new JTextField();
        src = new JTextField();
        dest = new JTextField();

        formPanel.add(makeLabel("Booking ID", labelFont));
        formPanel.add(id);
        formPanel.add(makeLabel("Customer Name", labelFont));
        formPanel.add(name);
        formPanel.add(makeLabel("Source City", labelFont));
        formPanel.add(src);
        formPanel.add(makeLabel("Destination City", labelFont));
        formPanel.add(dest);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(230, 240, 255));

        JButton book = new JButton("Book Ride");
        JButton view = new JButton("View Bookings");
        JButton cancel = new JButton("Cancel Booking");

        styleButton(book, new Color(40, 167, 69));
        styleButton(view, new Color(0, 123, 255));
        styleButton(cancel, new Color(220, 53, 69));

        buttonPanel.add(book);
        buttonPanel.add(view);
        buttonPanel.add(cancel);

        output = new JTextArea(8, 40);
        output.setEditable(false);
        output.setBackground(new Color(30, 30, 30));
        output.setForeground(Color.GREEN);
        output.setFont(new Font("Consolas", Font.PLAIN, 14));
        output.setBorder(BorderFactory.createTitledBorder("Output"));

        JScrollPane scroll = new JScrollPane(output);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        centerPanel.setBackground(new Color(230, 240, 255));

        centerPanel.add(formPanel, BorderLayout.NORTH);
        centerPanel.add(buttonPanel, BorderLayout.CENTER);
        centerPanel.add(scroll, BorderLayout.SOUTH);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        frame.add(mainPanel);

        // 🔹 Actions
        book.addActionListener(e -> execute("BOOK"));
        view.addActionListener(e -> execute("VIEW"));
        cancel.addActionListener(e -> execute("CANCEL"));

        frame.setVisible(true);
    }

    JLabel makeLabel(String text, Font f) {
        JLabel l = new JLabel(text);
        l.setFont(f);
        return l;
    }

    void styleButton(JButton b, Color c) {
        b.setBackground(c);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
    }

    // ✅ FIXED EXECUTION (MODULE-SAFE)
    void execute(String cmd) {
        try {
            // ✔ booking module ka apna input
            FileWriter fw = new FileWriter("../data/booking_input.txt");

            if (cmd.equals("BOOK")) {
                fw.write("BOOK " + id.getText() + " "
                        + name.getText() + " "
                        + src.getText() + " "
                        + dest.getText());
            } else if (cmd.equals("CANCEL")) {
                fw.write("CANCEL " + id.getText());
            } else {
                fw.write("VIEW");
            }
            fw.close();

            // ✔ correct exe
            ProcessBuilder pb = new ProcessBuilder("../cpp/booking.exe");
            pb.start().waitFor();

            // ✔ booking module ka apna output
            BufferedReader br =
                    new BufferedReader(new FileReader("../data/booking_output.txt"));

            output.setForeground(Color.GREEN);
            output.setText("");

            String line;
            while ((line = br.readLine()) != null) {
                output.append(line + "\n");
            }
            br.close();

        } catch (Exception ex) {
            output.setForeground(Color.RED);
            output.setText("ERROR: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new BookingUI();
    }
}
