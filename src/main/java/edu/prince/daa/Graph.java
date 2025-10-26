package edu.prince.daa;

import java.util.ArrayList;
import java.util.List;

/**
 * Graph
 * Represents a weighted undirected graph as needed by the assignment.
 *
 * - id: graph identifier (from input JSON)
 * - nodes: list of vertex labels (strings)
 * - edges: list of Edge objects
 *
 * This class includes small helper methods useful in tests and reporting.
 */
public class Graph {
    public int id;
    public List<String> nodes;
    public List<Edge> edges;

    public Graph() {
        // Gson needs a no-arg constructor for deserialization
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public Graph(int id, List<String> nodes, List<Edge> edges) {
        this.id = id;
        this.nodes = nodes;
        this.edges = edges;
    }

    public int numVertices() {
        return nodes == null ? 0 : nodes.size();
    }

    public int numEdges() {
        return edges == null ? 0 : edges.size();
    }
}
