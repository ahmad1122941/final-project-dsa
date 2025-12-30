import javax.swing.*;
import java.awt.*;

public class MainDashboard {

    public MainDashboard() {
        JFrame frame = new JFrame("🚀 Smart Transport Management - Dashboard");
        frame.setSize(600, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridLayout(9, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        mainPanel.setBackground(new Color(245, 245, 250));

        Font btnFont = new Font("Segoe UI", Font.BOLD, 18);

        JButton btnCityGraph = new JButton("City Graph Module");
        JButton btnVehicle = new JButton("Vehicle Module");
        JButton btnTraffic = new JButton("Traffic Module");
        JButton btnScheduler = new JButton("Scheduler Module");
        JButton btnShortestPath = new JButton("Shortest Path Module");
        JButton btnRoute = new JButton("Route Module");
        JButton btnFare = new JButton("Fare Module");
        JButton btnBooking = new JButton("Booking Module");
        JButton btnReport = new JButton("Report Module");

        JButton[] buttons = {btnCityGraph, btnVehicle, btnTraffic, btnScheduler,
                             btnShortestPath, btnRoute, btnFare, btnBooking, btnReport};

        Color[] colors = {
            new Color(40, 167, 69), new Color(0, 123, 255), new Color(255, 193, 7),
            new Color(23, 162, 184), new Color(108, 117, 125), new Color(255, 87, 51),
            new Color(102, 16, 242), new Color(255, 51, 153), new Color(0, 102, 204)
        };

        for (int i = 0; i < buttons.length; i++) {
            JButton btn = buttons[i];
            btn.setFont(btnFont);
            btn.setBackground(colors[i]);
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            int index = i; // for lambda
            btn.addActionListener(e -> launchModule(index));
            mainPanel.add(btn);
        }

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    // Launch module based on button index
    private void launchModule(int index) {
        switch(index) {
            case 0 -> CityGraphUI.run();
            case 1 -> VehicleUI.run();
            case 2 -> TrafficUI.run();
            case 3 -> SchedulerUI.run();
            case 4 -> ShortestPath.run();
            case 5 -> RouteUI.run();
            case 6 -> FareUI.run();
            case 7 -> BookingUI.run();
            case 8 -> ReportUI.run();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainDashboard::new);
    }
}
