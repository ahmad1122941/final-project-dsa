import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class ShortestPathModule {

    // ===== Inner Classes =====
    static class City {
        int id; String name; int x, y;
        public City(int id, String name, int x, int y) { this.id=id; this.name=name; this.x=x; this.y=y; }
    }

    static class Edge { int to, weight; public Edge(int to, int weight){ this.to=to; this.weight=weight; } }

    static class CityDist { int id, dist; CityDist(int id, int dist){ this.id=id; this.dist=dist; } }

    // ===== Module Components =====
    private JPanel mainPanel;
    private JComboBox<String> startCombo, endCombo;
    private JButton findBtn, resetBtn;
    private JLabel resultLabel;
    private DrawPanel mapPanel;

    // ===== Data =====
    private List<City> cities = new ArrayList<>();
    private List<List<Edge>> adj = new ArrayList<>();
    private int selectedStart=-1, selectedEnd=-1;
    private List<Integer> shortestPath = new ArrayList<>();

    // ===== Constructor =====
    public ShortestPathModule() {
        mainPanel = new JPanel(new BorderLayout());

        startCombo = new JComboBox<>();
        endCombo = new JComboBox<>();
        mapPanel = new DrawPanel();

        initData();  // Initialize cities and adjacency list

        // ===== Controls Panel =====
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setPreferredSize(new Dimension(220, 600));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel title = new JLabel("Shortest Path Finder");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        controlPanel.add(title);
        controlPanel.add(Box.createVerticalStrut(15));

        controlPanel.add(new JLabel("Start City:"));
        controlPanel.add(startCombo);
        controlPanel.add(Box.createVerticalStrut(10));

        controlPanel.add(new JLabel("End City:"));
        controlPanel.add(endCombo);
        controlPanel.add(Box.createVerticalStrut(20));

        findBtn = new JButton("Find Path");
        resetBtn = new JButton("Reset Map");
        controlPanel.add(findBtn);
        controlPanel.add(Box.createVerticalStrut(10));
        controlPanel.add(resetBtn);
        controlPanel.add(Box.createVerticalStrut(20));

        resultLabel = new JLabel("<html>Status: Ready</html>");
        resultLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        controlPanel.add(resultLabel);

        mainPanel.add(controlPanel, BorderLayout.WEST);
        mainPanel.add(mapPanel, BorderLayout.CENTER);

        // ===== Button Actions =====
        findBtn.addActionListener(e -> findPath());
        resetBtn.addActionListener(e -> resetMap());
    }

    // ===== Initialize Cities & Graph =====
    private void initData() {
        String[] names = {"Lahore","Karachi","Islamabad","Peshawar","Quetta","Multan",
                "Faisalabad","Rawalpindi","Sialkot","Gujranwala","Sargodha",
                "Hyderabad","Sukkur","Larkana","Bahawalpur","D.G. Khan",
                "Mardan","Mingora","Abbottabad","Haripur","Mansehra",
                "Chakwal","Jhelum","Gujrat","Sahiwal","Okara","Kasur",
                "Sheikhupura","Jhang","Mianwali","Bannu","Kohat",
                "D.I. Khan","Rahim Yar Khan","Sadiqabad","Jacobabad",
                "Khuzdar","Turbat","Gwadar"};

        Random rand = new Random(42);
        for(int i=0;i<names.length;i++){
            int x = 50 + rand.nextInt(650);
            int y = 50 + rand.nextInt(500);
            cities.add(new City(i,names[i],x,y));
            adj.add(new ArrayList<>());
            startCombo.addItem(names[i]);
            endCombo.addItem(names[i]);
        }

        // Connect each city to 3–4 nearest cities
        for(int i=0;i<cities.size();i++){
            City c1=cities.get(i);
            PriorityQueue<CityDist> pq=new PriorityQueue<>(Comparator.comparingInt(a->a.dist));
            for(int j=0;j<cities.size();j++){
                if(i==j) continue;
                City c2=cities.get(j);
                int d=(int)Math.hypot(c1.x-c2.x, c1.y-c2.y);
                pq.add(new CityDist(j,d));
            }
            int connections=0;
            while(!pq.isEmpty() && connections<4){
                CityDist cd=pq.poll(); int v=cd.id; int w=cd.dist/10;
                adj.get(i).add(new Edge(v,w));
                adj.get(v).add(new Edge(i,w));
                connections++;
            }
        }
    }

    // ===== Find Shortest Path (Dijkstra) =====
    private void findPath() {
        int start=startCombo.getSelectedIndex();
        int end=endCombo.getSelectedIndex();
        if(start==-1 || end==-1) return;

        selectedStart=start; selectedEnd=end;

        int n=cities.size();
        int[] dist=new int[n], prev=new int[n];
        Arrays.fill(dist,Integer.MAX_VALUE); Arrays.fill(prev,-1);
        PriorityQueue<int[]> pq=new PriorityQueue<>(Comparator.comparingInt(a->a[0]));
        dist[start]=0; pq.add(new int[]{0,start});

        while(!pq.isEmpty()){
            int[] cur=pq.poll(); int u=cur[1];
            for(Edge e: adj.get(u)){
                int alt=dist[u]+e.weight;
                if(alt<dist[e.to]){
                    dist[e.to]=alt; prev[e.to]=u; pq.add(new int[]{alt,e.to});
                }
            }
        }

        shortestPath.clear();
        for(int at=end; at!=-1; at=prev[at]) shortestPath.add(0,at);

        resultLabel.setText("<html>Distance: "+dist[end]+" km</html>");
        mapPanel.repaint();
    }

    private void resetMap() {
        selectedStart=-1; selectedEnd=-1; shortestPath.clear();
        startCombo.setSelectedIndex(-1); endCombo.setSelectedIndex(-1);
        resultLabel.setText("Status: Ready");
        mapPanel.repaint();
    }

    // ===== Draw Panel =====
    private class DrawPanel extends JPanel {
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2=(Graphics2D) g;

            // Draw all edges
            g2.setColor(Color.LIGHT_GRAY);
            for(int i=0;i<adj.size();i++){
                for(Edge e: adj.get(i)){
                    City a=cities.get(i), b=cities.get(e.to);
                    g2.drawLine(a.x,a.y,b.x,b.y);
                }
            }

            // Draw shortest path
            if(shortestPath.size()>1){
                g2.setColor(Color.BLUE);
                g2.setStroke(new BasicStroke(4));
                for(int i=0;i<shortestPath.size()-1;i++){
                    City a = cities.get(shortestPath.get(i));
                    City b = cities.get(shortestPath.get(i+1));

                    g2.drawLine(a.x,a.y,b.x,b.y);
                }
            }

            // Draw cities
            for(City c:cities){
                g2.setColor(Color.BLACK);
                g2.fillOval(c.x-5,c.y-5,10,10);
                g2.drawString(c.name,c.x+8,c.y+4);
            }
        }
    }

    // ===== Getter for Dashboard =====
    public JPanel getMainPanel(){ return mainPanel; }

}
