import javax.swing.*;

public class VehicleUI {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Vehicle Management System");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Vehicle Module GUI Working", SwingConstants.CENTER);
        frame.add(label);

        frame.setVisible(true);
    }
}
