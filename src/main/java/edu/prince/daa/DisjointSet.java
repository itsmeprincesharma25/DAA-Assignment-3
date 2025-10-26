package edu.prince.daa;

import java.util.HashMap;
import java.util.Map;

/**
 * DisjointSet (Union-Find) with path compression and union by rank.
 *
 * Purpose:
 * - Used by Kruskal's algorithm to quickly determine whether adding an edge will create a cycle.
 * - Instrumentation counters (public) allow the assignment to record operation counts:
 *   - findCount: how many times find() is called (proxy for comparisons)
 *   - unionCount: how many times unions merged two different sets
 *
 * Implementation notes (easy to explain):
 * - makeSet: initializes a vertex as its own parent with rank 0.
 * - find: recursively finds root and compresses the path.
 * - union: merges two roots using rank heuristic to keep tree shallow.
 */
public class DisjointSet {
    private final Map<String, String> parent = new HashMap<>();
    private final Map<String, Integer> rank = new HashMap<>();

    // Public counters for instrumentation and reporting (assignment requirement)
    public long findCount = 0;
    public long unionCount = 0;

    public void makeSet(String vertex) {
        parent.put(vertex, vertex);
        rank.put(vertex, 0);
    }

    public String find(String vertex) {
        // Count every find invocation for operation analysis
        findCount++;
        String p = parent.get(vertex);
        if (p == null) return null;
        if (!p.equals(vertex)) {
            String root = find(p);
            parent.put(vertex, root); // path compression
            return root;
        }
        return p;
    }

    public void union(String v1, String v2) {
        String r1 = find(v1);
        String r2 = find(v2);
        if (r1 == null || r2 == null) return;
        if (r1.equals(r2)) return;

        // A real merge happens -> increment union counter
        unionCount++;

        int rank1 = rank.getOrDefault(r1, 0);
        int rank2 = rank.getOrDefault(r2, 0);

        // Union by rank to keep tree height small
        if (rank1 < rank2) {
            parent.put(r1, r2);
        } else if (rank1 > rank2) {
            parent.put(r2, r1);
        } else {
            parent.put(r2, r1);
            rank.put(r1, rank1 + 1);
        }
    }
}
