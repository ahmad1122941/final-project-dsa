#include <iostream>
#include <fstream>
#include <map>
#include <vector>
#include <queue>
#include <cstdlib>
using namespace std;

struct Edge {
    string to;
    int distance;
};

void runTraffic() {
    ifstream in("../data/traffic_input.txt");
    ofstream out("../data/traffic_output.txt");

    if(!in){
        out << "ERROR: traffic_input.txt not found\n";
        return;
    }

    string srcCity, destCity;
    in >> srcCity >> destCity;

    map<string, vector<Edge>> graph;

    // ===== COMPLETE CONNECTED GRAPH =====
    graph["Islamabad"] = {{"Rawalpindi",15},{"Lahore",380},{"Peshawar",180}};
    graph["Rawalpindi"] = {{"Islamabad",15},{"Chakwal",90},{"Sargodha",220}};
    graph["Lahore"] = {{"Islamabad",380},{"Faisalabad",180},{"Sialkot",125}};
    graph["Faisalabad"] = {{"Lahore",180},{"Sargodha",130},{"Multan",250}};
    graph["Sargodha"] = {{"Faisalabad",130},{"Rawalpindi",220}};
    graph["Multan"] = {{"Faisalabad",250},{"Bahawalpur",100},{"DGKhan",250}};
    graph["Bahawalpur"] = {{"Multan",100},{"RahimYarKhan",180}};
    graph["RahimYarKhan"] = {{"Bahawalpur",180},{"Sukkur",330}};
    graph["Sukkur"] = {{"RahimYarKhan",330},{"Hyderabad",350},{"Larkana",220}};
    graph["Hyderabad"] = {{"Sukkur",350},{"Karachi",160}};
    graph["Karachi"] = {{"Hyderabad",160},{"Gwadar",620}};
    graph["Gwadar"] = {{"Karachi",620},{"Quetta",650}};
    graph["Quetta"] = {{"Gwadar",650},{"Khuzdar",300}};
    graph["Khuzdar"] = {{"Quetta",300},{"Kandahar",400}};
    graph["Kandahar"] = {{"Khuzdar",400}};
    graph["Peshawar"] = {{"Islamabad",180},{"Mardan",70}};
    graph["Mardan"] = {{"Peshawar",70},{"Swat",140}};
    graph["Swat"] = {{"Mardan",140}};
    graph["Sialkot"] = {{"Lahore",125},{"Gujranwala",45}};
    graph["Gujranwala"] = {{"Sialkot",45},{"Lahore",70}};
    graph["DGKhan"] = {{"Multan",250}};
    graph["Larkana"] = {{"Sukkur",220}};
    graph["MirpurKhas"] = {{"Hyderabad",220}};

    // ===== TRAFFIC =====
    map<string,int> traffic;
    for(auto &c : graph)
        traffic[c.first] = rand()%21 + 5;

    map<string,int> dist, baseDist, extraDist;
    for(auto &p : graph){
        dist[p.first] = 1e9;
        baseDist[p.first] = 0;
        extraDist[p.first] = 0;
    }

    dist[srcCity] = 0;

    priority_queue<pair<int,string>,
        vector<pair<int,string>>,
        greater<pair<int,string>>> pq;

    pq.push(make_pair(0, srcCity));

    while(!pq.empty()){
        pair<int,string> top = pq.top(); pq.pop();
        int d = top.first;
        string u = top.second;

        if(d > dist[u]) continue;

        for(auto &e : graph[u]){
            int extra = (e.distance * traffic[e.to]) / 100;
            int total = e.distance + extra;

            if(dist[e.to] > dist[u] + total){
                dist[e.to] = dist[u] + total;
                baseDist[e.to] = baseDist[u] + e.distance;
                extraDist[e.to] = extraDist[u] + extra;
                pq.push(make_pair(dist[e.to], e.to));
            }
        }
    }

    // ===== OUTPUT (SAME FORMAT) =====
    out << "Source City        : " << srcCity << endl;
    out << "Destination City   : " << destCity << endl;
    out << "----------------------------------" << endl;
    out << "Actual Distance    : " << baseDist[destCity] << " km" << endl;
    out << "Traffic Percentage : " << traffic[destCity] << " %" << endl;
    out << "Extra Distance     : " << extraDist[destCity] << " km" << endl;
    out << "----------------------------------" << endl;
    out << "Traffic Aware Total: " << dist[destCity] << " km" << endl;
}
