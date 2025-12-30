#include <bits/stdc++.h>
using namespace std;

// Simple Edge structure
struct Edge {
    string dest;
    int distance;
};

// Cities and their visual routes
vector<string> cities = {
    "Lahore", "Karachi", "Islamabad", "Peshawar", "Quetta", "Multan",
    "Faisalabad", "Rawalpindi", "Sialkot", "Gujranwala", "Sargodha",
    "Hyderabad", "Sukkur", "Larkana", "Bahawalpur", "D.G. Khan",
    "Mardan", "Mingora", "Abbottabad", "Haripur", "Mansehra",
    "Chakwal", "Jhelum", "Gujrat", "Sahiwal", "Okara", "Kasur",
    "Sheikhupura", "Jhang", "Mianwali", "Bannu", "Kohat", "D.I. Khan",
    "Rahim Yar Khan", "Sadiqabad", "Jacobabad", "Khuzdar", "Turbat", "Gwadar",
    "Panjgur", "Zhob", "Loralai", "Sibi", "Nawabshah", "Mirpur Khas", "Tando Adam",
    "Chiniot", "Khanewal", "Vehari", "Nowshera", "Swabi"
};

// 🔴 renamed global variable
map<string, vector<Edge>> cityRoutes;

void initRoutes() {
    for (size_t i=0;i<cities.size()-1;i++){
        cityRoutes[cities[i]].push_back({cities[i+1], 50});
        cityRoutes[cities[i+1]].push_back({cities[i], 50});
    }
}

// 🔴 ONLY CHANGE: main → runCityGraph()
void runCityGraph() {
    initRoutes();

    ifstream fin("data/input.txt");
    string src, dest;
    getline(fin, src);
    getline(fin, dest);
    fin.close();

    vector<string> path;
    path.push_back(src);

    for (size_t i=0;i<cities.size();i++){
        if(cities[i]==src){
            for(size_t j=i+1;j<cities.size();j++){
                path.push_back(cities[j]);
                if(cities[j]==dest) break;
            }
            break;
        }
    }

    int totalDistance = (int)path.size()*50;

    ofstream fout("data/output.txt");
    fout << totalDistance << "\n";
    for(size_t i=0;i<path.size();i++){
        fout << path[i];
        if(i+1<path.size()) fout << ",";
    }
    fout << "\n";
    fout.close();
}
