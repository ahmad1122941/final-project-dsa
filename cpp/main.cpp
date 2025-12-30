#include <iostream>
using namespace std;

// Modules ke headers ya cpp include
void runBooking();
void runCityGraph();
void runVehicle();
void runTraffic();
void runScheduler();
void runShortestPath();
void runRoute();
void runFare();
void runReport();

int main() {

    cout << "Running Smart Transport Management System...\n";

    runCityGraph();
    runVehicle();
    runTraffic();
    runScheduler();
    runShortestPath();
    runRoute();
    runFare();
    runBooking();
    runReport();

    cout << "All modules executed successfully.\n";
    return 0;
}
