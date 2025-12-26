#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
using namespace std;

struct Booking {
    string id, name, src, dest;
};

vector<Booking> bookings;

// Load existing bookings
void loadBookings() {
    bookings.clear();
    ifstream fin("../data/bookings.txt");
    Booking b;
    while (fin >> b.id >> b.name >> b.src >> b.dest) {
        bookings.push_back(b);
    }
    fin.close();
}

// Save all bookings
void saveBookings() {
    ofstream fout("../data/bookings.txt");
    for (auto &b : bookings) {
        fout << b.id << " " << b.name << " "
             << b.src << " " << b.dest << endl;
    }
    fout.close();
}

int main() {
    ifstream in("../data/booking_input.txt");
    ofstream out("../data/booking_output.txt");

    if (!in) {
        out << "ERROR: Cannot open booking_input.txt\n";
        return 0;
    }

    loadBookings();

    string command;
    in >> command;

    if (command == "BOOK") {
        Booking b;
        in >> b.id >> b.name >> b.src >> b.dest;
        bookings.push_back(b);
        saveBookings();
        out << "Booking Confirmed for " << b.name << endl;
    }

    else if (command == "VIEW") {
        if (bookings.empty()) {
            out << "No bookings available\n";
        } else {
            for (auto &b : bookings) {
                out << b.id << " | " << b.name
                    << " | " << b.src << " -> " << b.dest << endl;
            }
        }
    }

    else if (command == "CANCEL") {
        string id;
        in >> id;
        bool found = false;
        for (auto it = bookings.begin(); it != bookings.end(); ++it) {
            if (it->id == id) {
                bookings.erase(it);
                found = true;
                break;
            }
        }
        saveBookings();
        if (found)
            out << "Booking Cancelled: " << id << endl;
        else
            out << "Booking ID not found\n";
    }

    in.close();
    out.close();
    return 0;
}
