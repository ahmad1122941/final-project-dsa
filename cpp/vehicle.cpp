#include <iostream>
#include <fstream>
#include <string>
using namespace std;

const int MAX = 100;
const int TABLE_SIZE = 10;

/* =====================
   VEHICLE STRUCT
   ===================== */
struct Vehicle {
    int id;
    string type;
    int capacity;
};

/* =====================
   ARRAY STORAGE
   ===================== */
Vehicle vehicles[MAX];
int vehicleCount = 0;

/* =====================
   HASH TABLE
   ===================== */
int hashTable[TABLE_SIZE];

int hashFunction(int id) {
    return id % TABLE_SIZE;
}

/* =====================
   CORE FUNCTIONS
   ===================== */
void initHashTable() {
    for (int i = 0; i < TABLE_SIZE; i++)
        hashTable[i] = -1;
}

void addVehicle(int id, string type, int capacity) {
    if (vehicleCount >= MAX) return;

    vehicles[vehicleCount] = {id, type, capacity};
    int index = hashFunction(id);
    hashTable[index] = vehicleCount;
    vehicleCount++;
}

int searchVehicle(int id) {
    int index = hashFunction(id);
    int pos = hashTable[index];

    if (pos != -1 && vehicles[pos].id == id)
        return pos;

    return -1;
}

void deleteVehicle(int id) {
    int pos = searchVehicle(id);
    if (pos == -1) return;

    vehicles[pos] = vehicles[vehicleCount - 1];
    vehicleCount--;

    initHashTable();
    for (int i = 0; i < vehicleCount; i++) {
        hashTable[hashFunction(vehicles[i].id)] = i;
    }
}

/* =====================
   FILE-BASED INTEGRATION
   ===================== */
void runVehicle() {
    initHashTable();

    ifstream fin("data/input.txt");
    ofstream fout("data/output.txt");

    if (!fin) {
        fout << "ERROR: input.txt not found";
        return;
    }

    string command;
    fin >> command;

    if (command == "ADD") {
        int id, capacity;
        string type;
        fin >> id >> type >> capacity;

        addVehicle(id, type, capacity);
        fout << "Vehicle Added Successfully";

    } else if (command == "SEARCH") {
        int id;
        fin >> id;

        int pos = searchVehicle(id);
        if (pos != -1) {
            fout << "FOUND: ID=" << vehicles[pos].id
                 << " TYPE=" << vehicles[pos].type
                 << " CAPACITY=" << vehicles[pos].capacity;
        } else {
            fout << "Vehicle Not Found";
        }

    } else if (command == "DELETE") {
        int id;
        fin >> id;

        deleteVehicle(id);
        fout << "Vehicle Deleted Successfully";

    } else {
        fout << "Invalid Command";
    }

    fin.close();
    fout.close();
}
