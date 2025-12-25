#include <iostream>
#include <fstream>
#include <map>
#include <list>
#include <string>

using namespace std;

map<string, list<pair<string,int>>> graph; // Graph: city -> list of <destination, distance>

void addRoute(string src, string dest, int dist) {
    graph[src].push_back({dest, dist});
}

void deleteRoute(string src, string dest) {
    if(graph.find(src) != graph.end()) {
        graph[src].remove_if([dest](pair<string,int> p){ return p.first == dest; });
    }
}

void displayRoutes(ofstream &fout) {
    for(auto &v : graph) {
        for(auto &edge : v.second) {
            fout << v.first << " -> " << edge.first << " : " << edge.second << " km" << endl;
        }
    }
}

int main() {
    ifstream fin("data/input.txt");
    ofstream fout("data/routes.txt");

    if(!fin) {
        fout << "ERROR: input.txt not found";
        return 0;
    }

    string command;
    fin >> command;

    if(command == "ADD") {
        string src, dest;
        int dist;
        fin >> src >> dest >> dist;
        addRoute(src, dest, dist);
        fout << "Route Added Successfully";

    } else if(command == "DELETE") {
        string src, dest;
        fin >> src >> dest;
        deleteRoute(src, dest);
        fout << "Route Deleted Successfully";

    } else if(command == "VIEW") {
        displayRoutes(fout);

    } else {
        fout << "Invalid Command";
    }

    fin.close();
    fout.close();
    return 0;
}
