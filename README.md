# 🧮 DAA Assignment 3 — Optimization of a City Transportation Network (Minimum Spanning Tree)

**Student Name:** Prince Sharma  
**Course:** Design and Analysis of Algorithms  
**Topic:** Minimum Spanning Tree (Prim’s and Kruskal’s Algorithms)  
**Tool:** IntelliJ IDEA + Maven  
**Repository:** [DAA-Assignment-3](https://github.com/itsmeprincesharma25/DAA-Assignment-3)

---

## 🎯 Objective

To optimize a city’s transportation network by finding the **minimum set of roads** that connect all city districts with the **lowest total construction cost**, ensuring every district is reachable.

This problem is modeled as a **weighted undirected graph**, where:
- **Vertices** represent city districts.
- **Edges** represent possible roads.
- **Edge weights** represent construction costs.

We use **Prim’s** and **Kruskal’s** algorithms to compute the **Minimum Spanning Tree (MST)** and compare their performance.

---

## ⚙️ Algorithms Implemented

### 🧩 1. Prim’s Algorithm
- Starts from any vertex and grows the MST by adding the **smallest edge** connecting the tree to a new vertex.
- Uses a **min-heap (PriorityQueue)** for efficiency.
- **Time complexity:** `O(E log V)`
- **Space complexity:** `O(V + E)`

### 🧮 2. Kruskal’s Algorithm
- Sorts all edges by increasing weight.
- Adds edges to MST if they don’t form a cycle, using a **Disjoint Set Union (Union-Find)** structure.
- **Time complexity:** `O(E log E)`
- **Space complexity:** `O(V + E)`

---

## 📂 Input Data

Graphs were loaded from [`src/main/resources/input/input.json`](src/main/resources/input/input.json).

### Dataset Summary

| Graph ID | Vertices | Edges | Description |
|-----------|-----------|--------|--------------|
| 1 | 5 | 7 | Medium network of 5 districts |
| 2 | 4 | 5 | Small network of 4 districts |

---

## 📊 Experimental Results

Results were recorded in [`src/main/resources/output/output.json`](src/main/resources/output/output.json).

| Graph ID | Algorithm | MST Total Cost | Execution Time (ms) | Key Ops / Comparisons | Heap/Union Ops |
|-----------|------------|----------------|----------------------|------------------------|----------------|
| 1 | **Prim** | 16 | 0.725 | 7 comparisons, 7 key updates | 14 heap ops |
| 1 | **Kruskal** | 16 | 0.140 | 5 edge comparisons | 23 finds, 4 unions |
| 2 | **Prim** | 6 | 0.015 | 5 comparisons, 5 key updates | 10 heap ops |
| 2 | **Kruskal** | 6 | 0.010 | 3 edge comparisons | 16 finds, 3 unions |

✅ Both algorithms produced **identical MST costs**, confirming correctness.  
The **structure of edges** differs slightly, which is normal due to edge selection order.

---

## 🧠 Analysis & Comparison

| Criterion | **Prim’s Algorithm** | **Kruskal’s Algorithm** |
|------------|----------------------|--------------------------|
| Approach | Grows MST from one vertex | Adds smallest non-cyclic edges |
| Data Structure | Priority Queue (Min-Heap) | Disjoint Set (Union-Find) |
| Suitable for | Dense Graphs | Sparse Graphs |
| Time Complexity | O(E log V) | O(E log E) |
| Space Complexity | O(V + E) | O(V + E) |
| Ease of Implementation | Slightly complex (heap updates) | Simple edge sorting |
| Observed Speed | Slower for small graphs | Slightly faster in this dataset |

**Observation:**
- Kruskal’s algorithm performed faster on smaller graphs due to simpler edge sorting.
- Prim’s algorithm performs competitively on larger, dense graphs because of efficient heap usage.

---

## 🧾 Conclusions

- Both **Prim’s** and **Kruskal’s** algorithms successfully produced MSTs with **equal total costs**, verifying correctness.
- **Kruskal’s algorithm** showed **slightly better performance** in sparse graphs.
- For **dense graphs**, **Prim’s algorithm** tends to be more efficient.
- This project highlights the trade-offs between heap operations and union–find operations in graph optimization.

---

## 🧪 Testing Summary

- **Correctness Tests**
  - Total cost of MST is identical for both algorithms.
  - MST edges = V − 1 (no cycles).
  - Graph connectivity verified.
- **Performance Tests**
  - Execution times measured in milliseconds.
  - Operation counts recorded (comparisons, unions, key updates).

---

## 🧰 Technologies Used
- **Language:** Java 17
- **IDE:** IntelliJ IDEA
- **Build Tool:** Apache Maven
- **Libraries:** Gson (for JSON parsing), JUnit (for testing)

---

## 🏅 Bonus Section — Graph Design and Integration (10%)

As part of the bonus task, a fully object-oriented **Graph** structure was implemented in Java using the `Graph.java` and `Edge.java` classes.  
The graph data is loaded dynamically from JSON and represented through an adjacency list, verified by a printed structure before each MST computation.  
Both **Prim’s** and **Kruskal’s** algorithms operate directly on this custom `Graph` class, confirming proper integration and modular OOP design.  
This implementation fulfills all the bonus requirements, demonstrating clear graph visualization and seamless algorithm integration.

---

## 📂 Repository Structure

```bash
DAA-Assignment3/
├── pom.xml
├── README.md
├── src/
│   ├── main/
│   │   ├── java/edu/prince/daa/
│   │   │   ├── DisjointSet.java
│   │   │   ├── Edge.java
│   │   │   ├── Graph.java
│   │   │   ├── GraphLoader.java
│   │   │   ├── KruskalMST.java
│   │   │   ├── PrimMST.java
│   │   │   └── MainRunner.java
│   └── resources/
│       ├── input/input.json
│       └── output/output.json
│       └── output/results_summary.csv
└── target/


---

## 🏁 How to Run

```bash
# Compile
mvn -q clean compile

# Execute the program
mvn -q exec:java -Dexec.mainClass="edu.prince.daa.MainRunner"
