# final-project-dsa
smart Transport Management System Project - Java GUI + C++ DSA
# 🚦 Smart Transport Management System
**(Java GUI + C++ Data Structures & Algorithms Integrated Project)**

---

## 📌 Project Overview

The **Smart Transport Management System** is a real-world inspired, unified software application designed to manage and simulate urban transport operations efficiently.

The system is developed using:
- **Java** for GUI, user interaction, and visualization
- **C++** for core Data Structures and Algorithms (DSA)

All modules are fully integrated and operate as **one single application**, not as separate mini-projects.

---

## 🛠️ Technologies Used

| Layer | Technology |
|------|-----------|
| Frontend | Java Swing |
| Backend | C++ (MinGW Compiler) |
| Integration | File-based Communication |
| IDE | Visual Studio Code |
| Version Control | Git & GitHub |

---

## 🧩 System Architecture

Java GUI
↓
data/input.txt
↓
C++ Executable (DSA + Algorithms)
↓
data/output.txt / system_report.txt
↓
Java GUI (Visualization)

markdown
Copy code

✔ Java and C++ work as a **coordinated system**  
✔ Clear separation between frontend and backend

---

## ▶️ Module Execution Sequence (IMPORTANT)

The system modules are executed in the following **correct sequence**:

1. `CityGraphUI.java
2. `VehicleUI.java
3. `TrafficUI.java
4. `SchedulerUI.java
5. `ShortestPath.java
6. `RouteUI.java
7. `FareUI.java
8. `BookingUI.java
9. `ReportUI.java

This sequence ensures proper data flow and realistic system behavior.

---

## 📂 Project Folder Structure

smart-transport-management-system/
│
├── cpp/
│ ├── citygraph.cpp
│ ├── vehicle.cpp
│ ├── traffic.cpp
│ ├── scheduler.cpp
│ ├── shortestpath.cpp
│ ├── route.cpp
│ ├── fare.cpp
│ ├── booking.cpp
│ └── report.cpp
│
├── java/
│ ├── CityGraphUI.java
│ ├── VehicleUI.java
│ ├── TrafficUI.java
│ ├── SchedulerUI.java
│ ├── ShortestPathUI.java
│ ├── RouteUI.java
│ ├── FareUI.java
│ ├── BookingUI.java
│ └── ReportUI.java
│
├── data/
│ ├── vehicles.txt
│ ├── routes.txt
│ ├── bookings.txt
│ ├── fare.txt
│ ├── traffic.txt
│ ├── scheduler.txt
│ ├── shortestpath.txt
│ └── system_report.txt
│
└── README.md

yaml
Copy code

---

## 🔢 Project Modules (Total: 9)

---

### 🌍 Module 1: City Graph Visualization
**Files:** `citygraph.cpp`, `CityGraphUI.java`

**DSA Used**
- Level-2: Graph (Adjacency List)
- Level-1: Lists

**Functionality**
- Display cities as nodes
- Display routes as edges

**Purpose**
- Visual representation of transport network

---

### 🚗 Module 2: Vehicle Management System
**Files:** `vehicle.cpp`, `VehicleUI.java`

**DSA Used**
- Level-1: Arrays, File Handling
- Level-2: Hash Table (unordered_map)

**Functionality**
- Add vehicles
- Search vehicles
- Delete vehicles

---

### 🚦 Module 3: Traffic-Aware Routing System
**Files:** `traffic.cpp`, `TrafficUI.java`

**DSA Used**
- Level-2: Graph, Priority Queue
- Level-1: Arrays

**Functionality**
- Actual distance calculation
- Traffic percentage adjustment
- Traffic-aware distance computation

---

### ⏱️ Module 4: Scheduler System
**Files:** `scheduler.cpp`, `SchedulerUI.java`

**DSA Used**
- Level-2: Priority Queue
- Level-1: Arrays

**Functionality**
- Peak hour detection
- Route scheduling analysis

---

### 📍 Module 5: Shortest Path System
**Files:** `shortestpath.cpp`

**DSA Used**
- Level-2: Graph, Priority Queue

**Algorithm**
- Dijkstra’s Algorithm

**Functionality**
- Shortest distance between two cities

---

### 🛣️ Module 6: Route Management System
**Files:** `route.cpp`, `RouteUI.java`

**DSA Used**
- Level-2: Graph
- Level-1: Arrays

**Functionality**
- Add and view routes between cities

---

### 💰 Module 7: Fare Calculation System
**Files:** `fare.cpp`, `FareUI.java`

**DSA Used**
- Level-1: Arrays
- Level-2: HashMap

**Functionality**
- Fare calculation based on distance
- Revenue summary

---

### 🚕 Module 8: Booking Management System
**Files:** `booking.cpp`, `BookingUI.java`

**DSA Used**
- Level-1: Queue
- Level-2: Hash Table

**Functionality**
- Book ride
- Cancel booking
- View bookings

---

### 📊 Module 9: System Analytics & Report Module
**Files:** `report.cpp`, `ReportUI.java`

**DSA Used**
- Level-1: Arrays, File Handling
- Level-2: HashMap

**Functionality**
- Pre-stored 40–50 system records
- Module-wise statistics
- Append new system reports
- System health monitoring

---

## 🔗 Integration Method

- Java GUI writes commands to `data/input.txt`
- C++ backend reads input and processes data using DSAs
- C++ writes results to output files
- Java GUI reads and displays results

✔ Stable  
✔ Simple  
✔ Platform-independent  

---
