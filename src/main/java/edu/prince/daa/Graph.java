package edu.prince.daa;

import java.util.*;

/**
 * Graph
 * - Simple, assignment-friendly representation of an undirected weighted graph.
 * - Keeps fields public so Gson can populate them directly.
 * - Provides an adjacency list and utilities (buildAdjacencyList, printGraph).
 */
public class Graph {
    // Keep these public for Gson deserialization and for easier access in MainRunner
    public int id;
    public List<String> nodes;
    public List<Edge> edges;

    // adjacency list used by Prim and for printing (maps vertex -> list of incident edges)
    private final Map<String, List<Edge>> adj = new HashMap<>();

    // No-arg constructor needed by Gson
    public Graph() {
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    // Optional convenience constructor
    public Graph(int id, List<String> nodes, List<Edge> edges) {
        this.id = id;
        this.nodes = nodes != null ? nodes : new ArrayList<>();
        this.edges = edges != null ? edges : new ArrayList<>();
        buildAdjacencyList();
    }

    /**
     * Build (or rebuild) the adjacency list from edges.
     * Must be called after deserialization so adj is populated.
     */
    public void buildAdjacencyList() {
        adj.clear();
        if (nodes == null) return;
        // ensure all nodes exist in adj (so printGraph shows nodes with no edges too)
        for (String v : nodes) {
            adj.putIfAbsent(v, new ArrayList<>());
        }
        if (edges == null) return;
        for (Edge e : edges) {
            // defensive checks
            if (e == null || e.from == null || e.to == null) continue;
            adj.computeIfAbsent(e.from, k -> new ArrayList<>()).add(e);
            adj.computeIfAbsent(e.to, k -> new ArrayList<>()).add(e);
        }
    }

    /**
     * Print adjacency list. Builds adjacency lazily if necessary.
     */
    public void printGraph() {
        // If adjacency is empty (likely because Gson set fields without calling constructor),
        // build it now.
        if (adj.isEmpty()) buildAdjacencyList();

        System.out.println("Graph ID: " + id);
        for (String node : nodes) {
            System.out.print("  " + node + " -> ");
            List<Edge> connected = adj.getOrDefault(node, Collections.emptyList());
            for (Edge e : connected) {
                // print the neighbor vertex and weight
                String neighbor = e.from.equals(node) ? e.to : e.from;
                System.out.print(neighbor + "(" + e.weight + ") ");
            }
            System.out.println();
        }
    }

    // getters useful if you switch to private fields later
    public Map<String, List<Edge>> getAdjacencyList() {
        if (adj.isEmpty()) buildAdjacencyList();
        return adj;
    }

    public int numVertices() {
        return nodes == null ? 0 : nodes.size();
    }

    public int numEdges() {
        return edges == null ? 0 : edges.size();
    }
}
