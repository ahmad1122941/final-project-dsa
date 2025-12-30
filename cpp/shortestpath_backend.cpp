#include <bits/stdc++.h>
using namespace std;

struct Edge {
    int to;
    int weight;
};

struct City {
    int id;
    string name;
};

vector<City> cities;
vector<vector<Edge>> adj;

// ---------- Dijkstra ----------
vector<int> dijkstra(int start, int end) {
    int n = cities.size();
    vector<int> dist(n, INT_MAX);
    vector<int> prev(n, -1);

    priority_queue<pair<int,int>, vector<pair<int,int>>, greater<>> pq;

    dist[start] = 0;
    pq.push({0, start});

    while (!pq.empty()) {
        pair<int,int> top = pq.top();
        int d = top.first;
        int u = top.second;

        pq.pop();

        if (d > dist[u]) continue;
        if (u == end) break;

        for (auto &e : adj[u]) {
            int v = e.to;
            int nd = dist[u] + e.weight;
            if (nd < dist[v]) {
                dist[v] = nd;
                prev[v] = u;
                pq.push({nd, v});
            }
        }
    }

    vector<int> path;
    for (int at = end; at != -1; at = prev[at])
        path.push_back(at);

    reverse(path.begin(), path.end());
    return path;
}

// 🔴 ONLY CHANGE: main() → runShortestPath()
void runShortestPath() {

    vector<string> names = {
        "Lahore", "Karachi", "Islamabad", "Peshawar", "Quetta", "Multan",
        "Faisalabad", "Rawalpindi", "Sialkot", "Gujranwala", "Sargodha",
        "Hyderabad", "Sukkur", "Larkana", "Bahawalpur", "D.G. Khan",
        "Mardan", "Mingora", "Abbottabad", "Haripur", "Mansehra",
        "Chakwal", "Jhelum", "Gujrat", "Sahiwal", "Okara", "Kasur",
        "Sheikhupura", "Jhang", "Mianwali", "Bannu", "Kohat",
        "D.I. Khan", "Rahim Yar Khan", "Sadiqabad", "Jacobabad",
        "Khuzdar", "Turbat", "Gwadar"
    };

    int n = names.size();
    cities.resize(n);
    adj.resize(n);

    for (int i = 0; i < n; i++) {
        cities[i] = {i, names[i]};
    }

    // Simple graph
    for (int i = 0; i < n - 1; i++) {
        adj[i].push_back({i + 1, 10});
        adj[i + 1].push_back({i, 10});
    }

    cout << "Cities List:\n";
    for (int i = 0; i < n; i++)
        cout << i << ": " << cities[i].name << "\n";

    int start, end;
    cout << "\nEnter start city id: ";
    cin >> start;
    cout << "Enter end city id: ";
    cin >> end;

    vector<int> path = dijkstra(start, end);

    cout << "\nShortest Path:\n";
    for (size_t i = 0; i < path.size(); i++) {
        cout << cities[path[i]].name;
        if (i + 1 < path.size()) cout << " -> ";
    }

    cout << "\n";
}
