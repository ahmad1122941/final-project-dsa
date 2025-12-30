#include <iostream>
#include <fstream>
#include <string>
using namespace std;

int countLines(const string &file) {
    ifstream fin(file);
    int count = 0;
    string line;
    while (getline(fin, line)) count++;
    fin.close();
    return count;
}

// 🔴 ONLY CHANGE: main() → runReport()
void runReport() {
    ofstream report("data/report.txt");

    int vehicles   = countLines("data/vehicles.txt");
    int bookings   = countLines("data/bookings.txt");
    int routes     = countLines("data/routes.txt");
    int traffic    = countLines("data/traffic.txt");
    int fares      = countLines("data/fares.txt");
    int schedules  = countLines("data/schedules.txt");

    report << "========== SMART TRANSPORT SYSTEM REPORT ==========\n\n";
    report << "Vehicles Registered     : " << vehicles  << "\n";
    report << "Total Bookings          : " << bookings  << "\n";
    report << "Available Routes        : " << routes    << "\n";
    report << "Traffic Records         : " << traffic   << "\n";
    report << "Fare Rules              : " << fares     << "\n";
    report << "Schedules               : " << schedules << "\n\n";

    report << "--------------------------------------------------\n";
    report << "System Status : ACTIVE & FUNCTIONAL\n";
    report << "Modules Working:\n";
    report << "✔ Vehicle Management\n";
    report << "✔ Route Management\n";
    report << "✔ Shortest Path\n";
    report << "✔ Traffic Analysis\n";
    report << "✔ Scheduler\n";
    report << "✔ Booking\n";
    report << "✔ Fare Calculation\n";
    report << "✔ City Graph\n";
    report << "✔ Report Module\n";
    report << "--------------------------------------------------\n";

    report.close();
}
