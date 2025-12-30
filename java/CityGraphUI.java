import java.awt.*;
import java.util.*;
import javax.swing.*;

public class CityGraphUI extends JFrame {

    private JComboBox<String> srcCombo, destCombo;
    private JTextArea outputArea;
    private GraphPanel graphPanel;
    private java.util.List<String> activePath = new ArrayList<>();

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

    private Map<String, Point> cityPositions = new LinkedHashMap<>();
    private Map<String, java.util.List<Edge>> routes = new LinkedHashMap<>();

    static class Edge { String dest; int distance; Edge(String d,int w){dest=d;distance=w;} }

    private JPanel mainPanel;

    public CityGraphUI() {
        // Frame setup
        setTitle("Smart Transport Management - City Graph");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1150, 800);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));

        initData();
        setupUI();
        add(mainPanel);
    }

    private void initData() {
        Random rand = new Random(42);
        int padding=60, mapWidth=900, mapHeight=700;
        
        // 1. Positions set kar rahe hain
        for(String city: cityNames){
            int x=padding+rand.nextInt(mapWidth-2*padding);
            int y=padding+rand.nextInt(mapHeight-2*padding);
            cityPositions.put(city, new Point(x,y));
            routes.put(city, new ArrayList<>());
        }
        
        // 2. Graph Logic Change: Real Route Lagne ke liye
        // Pehle logic thi har city agli se connect, ab logic hai: Har city apni qareeb (closest) 4 cities se connect hogi.
        int neighborsToConnect = 4; 
        
        for(int i=0; i<cityNames.length; i++) {
            String cityA = cityNames[i];
            Point p1 = cityPositions.get(cityA);
            
            // List banayi gayi baki cities ki distance ke hisab se
            java.util.List<CityDistance> distances = new ArrayList<>();
            
            for(int j=0; j<cityNames.length; j++) {
                if(i == j) continue;
                String cityB = cityNames[j];
                Point p2 = cityPositions.get(cityB);
                double dist = p1.distance(p2);
                distances.add(new CityDistance(cityB, (int)dist));
            }
            
            // Sort by distance (Sabse qareeb shuru mein aayega)
            Collections.sort(distances, (a, b) -> a.dist - b.dist);
            
            // Top 4 qareeb cities ko connect karo
            for(int k=0; k<neighborsToConnect && k<distances.size(); k++) {
                CityDistance target = distances.get(k);
                
                // Edge A -> B
                routes.get(cityA).add(new Edge(target.cityName, target.dist));
                
                // Edge B -> A (Dono taraf se connect taaki route dono taraf chale)
                // Note: Ye ensure karega ke graph connected rahe
                if(routes.get(target.cityName).stream().noneMatch(e -> e.dest.equals(cityA))) {
                     routes.get(target.cityName).add(new Edge(cityA, target.dist));
                }
            }
        }
    }
    
    // Helper class for sorting cities by distance
    static class CityDistance {
        String cityName;
        int dist;
        CityDistance(String n, int d) { cityName = n; dist = d; }
    }

    private void setupUI() {
        JPanel controlPanel = new JPanel(new GridLayout(10,1,5,5));
        controlPanel.setPreferredSize(new Dimension(250,0));
        controlPanel.setBackground(new Color(240,240,240));

        srcCombo=new JComboBox<>(cityNames);
        destCombo=new JComboBox<>(cityNames);
        JButton highlightBtn=new JButton("Find Route");
        JButton resetBtn=new JButton("Reset Map");
        outputArea=new JTextArea(8,15);
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Consolas",Font.PLAIN,12));
        outputArea.setBackground(Color.WHITE);

        controlPanel.add(new JLabel("Source City:")); controlPanel.add(srcCombo);
        controlPanel.add(new JLabel("Destination City:")); controlPanel.add(destCombo);
        controlPanel.add(highlightBtn); controlPanel.add(resetBtn);
        controlPanel.add(new JLabel("Status:"));
        controlPanel.add(new JScrollPane(outputArea));

        graphPanel=new GraphPanel();
        graphPanel.setBackground(new Color(235,242,249));

        mainPanel.add(controlPanel, BorderLayout.WEST);
        mainPanel.add(graphPanel, BorderLayout.CENTER);

        highlightBtn.addActionListener(e -> calculatePathInJava());
        resetBtn.addActionListener(e -> { activePath.clear(); outputArea.setText(""); graphPanel.repaint(); });
    }

    private void calculatePathInJava() {
        String src = srcCombo.getSelectedItem().toString();
        String dest = destCombo.getSelectedItem().toString();

        outputArea.setText(""); 

        if (src.equals(dest)) {
            outputArea.setForeground(Color.BLUE);
            outputArea.setText("Source and Destination are same.");
            return;
        }

        // Logic to find shortest path (Hidden from output)
        Map<String, String> parentMap = new HashMap<>();
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.add(src);
        visited.add(src);
        parentMap.put(src, null);

        boolean found = false;

        while (!queue.isEmpty() && !found) {
            String currentCity = queue.poll();

            for (Edge edge : routes.get(currentCity)) {
                String neighbor = edge.dest;
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parentMap.put(neighbor, currentCity);
                    queue.add(neighbor);

                    if (neighbor.equals(dest)) {
                        found = true;
                        break;
                    }
                }
            }
        }

        activePath.clear();

        if (found) {
            java.util.List<String> pathList = new ArrayList<>();
            String curr = dest;
            while (curr != null) {
                pathList.add(curr);
                curr = parentMap.get(curr);
            }
            Collections.reverse(pathList);
            activePath = pathList;

            int totalDist = 0;
            for(int i=0; i<activePath.size()-1; i++){
                String c1 = activePath.get(i);
                String c2 = activePath.get(i+1);
                for(Edge e : routes.get(c1)){
                    if(e.dest.equals(c2)){
                        totalDist += e.distance;
                        break;
                    }
                }
            }

            // ✅ CHANGED: Output mein sirf Distance, Baaki kuch nahi.
            outputArea.setForeground(Color.BLACK);
            outputArea.setText("Total Distance: " + totalDist + " km");
        } else {
            outputArea.setForeground(Color.RED);
            outputArea.setText("No path found.");
        }
        
        graphPanel.repaint();
    }

    class GraphPanel extends JPanel{
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2=(Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw all edges (Lines)
            g2.setColor(new Color(200,200,200));
            for(String src: routes.keySet()){
                Point p1=cityPositions.get(src);
                for(Edge e: routes.get(src)){
                    Point p2=cityPositions.get(e.dest);
                    g2.drawLine(p1.x,p1.y,p2.x,p2.y);
                }
            }

            // Draw cities (Nodes)
            for(String city: cityNames){
                Point p=cityPositions.get(city);
                if(activePath.contains(city)){
                    g2.setColor(Color.RED);
                    g2.fillOval(p.x-7,p.y-7,14,14);
                } else {
                    g2.setColor(new Color(255,140,0));
                    g2.fillOval(p.x-5,p.y-5,10,10);
                }
                g2.setColor(Color.BLACK);
                g2.drawOval(p.x-6,p.y-6,12,12);
                g2.setFont(new Font("Arial",Font.BOLD,10));
                g2.drawString(city,p.x+8,p.y+4);
            }

            // Draw Highlighted Path
            if(activePath.size()>1){
                g2.setColor(Color.GREEN);
                g2.setStroke(new BasicStroke(3));
                for(int i=0;i<activePath.size()-1;i++){
                    Point p1=cityPositions.get(activePath.get(i));
                    Point p2=cityPositions.get(activePath.get(i+1));
                    g2.drawLine(p1.x,p1.y,p2.x,p2.y);
                }
            }
        }
    }

    public JPanel getMainPanel() { return mainPanel; }

    public static void run() {
        SwingUtilities.invokeLater(() -> new CityGraphUI().setVisible(true));
    }
}