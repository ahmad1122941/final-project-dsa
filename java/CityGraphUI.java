import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class CityGraphUI extends JFrame {

    private Map<String, Point> cityPositions = new LinkedHashMap<>();
    private Map<String, List<Edge>> routes = new LinkedHashMap<>();
    private String highlightSrc = "", highlightDest = "";
    private JTextField srcField, destField;
    private JTextArea outputArea;
    private GraphPanel graphPanel;

    // Edge class
    static class Edge {
        String dest;
        int distance;
        Edge(String dest, int distance) {
            this.dest = dest;
            this.distance = distance;
        }
    }

    public CityGraphUI() {
        setTitle("Smart Transport Management System - City Routes");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize cities & routes
        initCities();
        initRoutes();

        // Control panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(10,1,5,5));
        controlPanel.setPreferredSize(new Dimension(220, 0));

        srcField = new JTextField();
        destField = new JTextField();
        JButton highlightBtn = new JButton("Highlight Route");
        JButton viewBtn = new JButton("View Routes");

        outputArea = new JTextArea(6,15);
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Consolas", Font.BOLD, 13));
        outputArea.setBackground(new Color(240,240,240));

        controlPanel.add(new JLabel("Source City:"));
        controlPanel.add(srcField);
        controlPanel.add(new JLabel("Destination City:"));
        controlPanel.add(destField);
        controlPanel.add(highlightBtn);
        controlPanel.add(viewBtn);
        controlPanel.add(new JLabel("Output:"));
        controlPanel.add(new JScrollPane(outputArea));

        // Graph panel
        graphPanel = new GraphPanel();
        graphPanel.setBackground(new Color(245, 250, 255));

        add(controlPanel, BorderLayout.WEST);
        add(graphPanel, BorderLayout.CENTER);

        // Button actions
        highlightBtn.addActionListener(e -> highlightRoute());
        viewBtn.addActionListener(e -> viewRoutes());

        setVisible(true);
    }

    private void initCities() {
        // Predefined positions for 15 cities
        cityPositions.put("Islamabad", new Point(200,100));
        cityPositions.put("Rawalpindi", new Point(220,140));
        cityPositions.put("Lahore", new Point(500,180));
        cityPositions.put("Faisalabad", new Point(460,220));
        cityPositions.put("Karachi", new Point(500,450));
        cityPositions.put("Peshawar", new Point(150,80));
        cityPositions.put("Quetta", new Point(250,350));
        cityPositions.put("Multan", new Point(420,300));
        cityPositions.put("Sialkot", new Point(550,120));
        cityPositions.put("Bahawalpur", new Point(480,370));
        cityPositions.put("Sukkur", new Point(400,400));
        cityPositions.put("Hyderabad", new Point(520,420));
        cityPositions.put("Gilgit", new Point(100,50));
        cityPositions.put("Abbottabad", new Point(180,120));
        cityPositions.put("Mardan", new Point(130,100));

        // Initialize routes map
        for(String city : cityPositions.keySet()) {
            routes.put(city, new ArrayList<>());
        }
    }

    private void initRoutes() {
        // Predefined edges
        routes.get("Islamabad").add(new Edge("Rawalpindi",15));
        routes.get("Islamabad").add(new Edge("Lahore",375));
        routes.get("Rawalpindi").add(new Edge("Islamabad",15));
        routes.get("Rawalpindi").add(new Edge("Peshawar",180));
        routes.get("Lahore").add(new Edge("Islamabad",375));
        routes.get("Lahore").add(new Edge("Faisalabad",120));
        routes.get("Faisalabad").add(new Edge("Lahore",120));
        routes.get("Faisalabad").add(new Edge("Multan",250));
        routes.get("Karachi").add(new Edge("Hyderabad",165));
        routes.get("Hyderabad").add(new Edge("Karachi",165));
        routes.get("Multan").add(new Edge("Faisalabad",250));
        routes.get("Multan").add(new Edge("Bahawalpur",90));
        routes.get("Bahawalpur").add(new Edge("Multan",90));
        routes.get("Peshawar").add(new Edge("Rawalpindi",180));
        routes.get("Quetta").add(new Edge("Sukkur",300));
        routes.get("Sukkur").add(new Edge("Quetta",300));
        routes.get("Gilgit").add(new Edge("Abbottabad",150));
        routes.get("Abbottabad").add(new Edge("Gilgit",150));
        routes.get("Mardan").add(new Edge("Peshawar",50));
        routes.get("Peshawar").add(new Edge("Mardan",50));
        routes.get("Sialkot").add(new Edge("Lahore",120));
        routes.get("Lahore").add(new Edge("Sialkot",120));
    }

    private void highlightRoute() {
        String src = srcField.getText().trim();
        String dest = destField.getText().trim();

        if(!routes.containsKey(src) || routes.get(src).stream().noneMatch(e -> e.dest.equals(dest))) {
            outputArea.setForeground(Color.RED);
            outputArea.setText("Route not found: " + src + " -> " + dest);
            highlightSrc = "";
            highlightDest = "";
        } else {
            highlightSrc = src;
            highlightDest = dest;
            int dist = routes.get(src).stream().filter(e -> e.dest.equals(dest)).findFirst().get().distance;
            outputArea.setForeground(new Color(0,128,0)); // dark green
            outputArea.setText("Route highlighted: " + src + " -> " + dest + " : " + dist + " km");
        }
        graphPanel.repaint();
    }

    private void viewRoutes() {
        StringBuilder sb = new StringBuilder();
        for(String src: routes.keySet()){
            for(Edge e: routes.get(src)){
                sb.append(src).append(" -> ").append(e.dest).append(" : ").append(e.distance).append(" km\n");
            }
        }
        outputArea.setForeground(Color.BLACK);
        outputArea.setText(sb.length()==0?"No routes available":sb.toString());
        highlightSrc = "";
        highlightDest = "";
        graphPanel.repaint();
    }

    // Panel to draw graph
    class GraphPanel extends JPanel {
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(3));

            // Draw edges
            for(String src: routes.keySet()){
                Point p1 = cityPositions.get(src);
                for(Edge e: routes.get(src)){
                    Point p2 = cityPositions.get(e.dest);
                    if(src.equals(highlightSrc) && e.dest.equals(highlightDest))
                        g2.setColor(Color.GREEN);
                    else
                        g2.setColor(Color.GRAY);
                    g2.drawLine(p1.x, p1.y, p2.x, p2.y);

                    // Distance label offset slightly
                    int mx = (p1.x + p2.x)/2;
                    int my = (p1.y + p2.y)/2 - 5;
                    g2.setColor(Color.BLUE);
                    g2.setFont(new Font("Arial", Font.BOLD, 12));
                    g2.drawString(e.distance + " km", mx, my);
                }
            }

            // Draw nodes
            for(String city: cityPositions.keySet()){
                Point p = cityPositions.get(city);
                g2.setColor(Color.ORANGE);
                g2.fillOval(p.x-18,p.y-18,36,36);
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(2));
                g2.drawOval(p.x-18,p.y-18,36,36);
                g2.setFont(new Font("Arial", Font.BOLD, 12));
                g2.drawString(city, p.x-25, p.y-25);
            }
        }
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(CityGraphUI::new);
    }
}
