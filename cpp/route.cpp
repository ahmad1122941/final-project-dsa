#include <iostream>
#include <fstream>
#include <list>
#include <string>
using namespace std;

struct Route {
    string src;
    string dest;
    int dist;
};

// 🔴 renamed global variable
list<Route> routeList;

// Load existing routes from file
void loadRoutes() {
    routeList.clear();
    ifstream fin("data/routes.txt");
    if(!fin) return;
    string src,dest; int dist;
    while(fin >> src >> dest >> dist) {
        routeList.push_back({src,dest,dist});
    }
    fin.close();
}

// Save all routes to file
void saveRoutes() {
    ofstream fout("data/routes.txt");
    for(auto r : routeList)
        fout << r.src << " " << r.dest << " " << r.dist << endl;
    fout.close();
}

// Generate GUI-friendly output
void writeOutput(string msg) {
    ofstream fout("data/output.txt");
    fout << msg << endl;
    fout.close();
}

void writeAllRoutes() {
    ofstream fout("data/output.txt");
    if(routeList.empty()) {
        fout << "No routes available" << endl;
    } else {
        for(auto r : routeList)
            fout << r.src << " -> " << r.dest << " : " << r.dist << " km" << endl;
    }
    fout.close();
}

// 🔴 ONLY CHANGE: main → runRoute()
void runRoute() {
    loadRoutes();

    ifstream fin("data/input.txt");
    if(!fin) {
        writeOutput("ERROR: input.txt not found");
        return;
    }

    string command;
    fin >> command;

    if(command == "ADD") {
        string src,dest; int dist;
        fin >> src >> dest >> dist;
        routeList.push_back({src,dest,dist});
        saveRoutes();
        writeAllRoutes();

    } else if(command == "DELETE") {
        string src,dest;
        fin >> src >> dest;
        routeList.remove_if([&](Route r){ return r.src==src && r.dest==dest; });
        saveRoutes();
        writeAllRoutes();

    } else if(command == "VIEW") {
        writeAllRoutes();

    } else {
        writeOutput("Invalid Command");
    }

    fin.close();
}
