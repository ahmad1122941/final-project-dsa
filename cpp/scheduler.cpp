#include <bits/stdc++.h>
using namespace std;

// ===== Vehicle Struct =====
struct Vehicle {
    string id;
    int availableTime; // in minutes
};

// Comparator for priority queue (earliest available vehicle first)
struct CompareVehicle {
    bool operator()(Vehicle const &v1, Vehicle const &v2) {
        return v1.availableTime > v2.availableTime; 
    }
};

// 🔴 ONLY CHANGE: main() → runScheduler()
void runScheduler() {
    // ===== Sample Vehicles =====
    vector<Vehicle> vehicles = {
        {"V001", 0},
        {"V002", 0},
        {"V003", 0},
        {"V004", 0},
        {"V005", 0}
    };

    // ===== Priority Queue =====
    priority_queue<Vehicle, vector<Vehicle>, CompareVehicle> pq;
    for(auto &v : vehicles) pq.push(v);

    // ===== Read Bookings =====
    ifstream fin("../data/scheduler_input.txt");
    ofstream fout("../data/scheduler_output.txt");
    if(!fin) {
        fout << "Error: scheduler_input.txt not found!\n";
        return;
    }

    string bookingID, customer, src, dest;
    while(fin >> bookingID >> customer >> src >> dest) {
        // Get earliest available vehicle
        Vehicle v = pq.top(); pq.pop();

        // Random travel time (simulate) in minutes
        int travelTime = 60 + rand()%120; // 1–3 hours
        int startTime = v.availableTime;
        int arrivalTime = startTime + travelTime;

        // Format time in HH:MM AM/PM
        int startH = startTime / 60; int startM = startTime % 60;
        int arrivalH = arrivalTime / 60; int arrivalM = arrivalTime % 60;

        auto formatTime = [](int h, int m) {
            string period = (h>=12) ? "PM":"AM";
            h = h%12; if(h==0) h=12;
            char buf[10]; sprintf(buf,"%02d:%02d %s",h,m,period.c_str());
            return string(buf);
        };

        // Write schedule to output
        fout << "Booking ID   : " << bookingID << "\n";
        fout << "Customer     : " << customer << "\n";
        fout << "Vehicle ID   : " << v.id << "\n";
        fout << "Route        : " << src << " → " << dest << "\n";
        fout << "Expected Time: " << formatTime(startH,startM) << " - " 
             << formatTime(arrivalH,arrivalM) << "\n";
        fout << "------------------------------------\n";

        // Update vehicle availability
        v.availableTime = arrivalTime;
        pq.push(v);
    }

    fin.close(); fout.close();
}
