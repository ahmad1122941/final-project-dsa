#include <iostream>
#include <fstream>
#include <sstream>
using namespace std;

// 🔴 ONLY CHANGE: main() → runFare()
void runFare() {
    ifstream in("../data/fare_input.txt");
    ofstream out("../data/fare_output.txt");

    if (!in) {
        out << "ERROR: fare_input.txt not found\n";
        return;
    }

    string vehicleType;
    int distance;
    in >> vehicleType >> distance;

    double ratePerKm = 0;

    // Level-1 DSA logic (simple rule array)
    if (vehicleType == "Bike")
        ratePerKm = 20;
    else if (vehicleType == "Car")
        ratePerKm = 40;
    else if (vehicleType == "Bus")
        ratePerKm = 15;
    else {
        out << "Invalid vehicle type\n";
        return;
    }

    double fare = ratePerKm * distance;

    out << "Vehicle Type : " << vehicleType << endl;
    out << "Distance     : " << distance << " km\n";
    out << "Rate per km  : Rs " << ratePerKm << endl;
    out << "----------------------------\n";
    out << "Total Fare   : Rs " << fare << endl;

    in.close();
    out.close();
}
