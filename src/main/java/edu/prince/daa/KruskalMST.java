package edu.prince.daa;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * KruskalMST
 *
 * Implements Kruskal's algorithm to compute the Minimum Spanning Tree (MST)
 * for a given undirected weighted graph.
 *
 * Design notes (easy to explain in viva):
 * - Sort all edges by weight (ascending).
 * - Use DisjointSet (union-find) to detect cycles: if two vertices are already
 *   in the same set, adding the edge would form a cycle, so skip it.
 * - Stop when MST has V-1 edges (V = number of vertices).
 *
 * Instrumentation (assignment requirements):
 * - Records MST edges and total cost.
 * - Counts considered edges (edgeComparisons) and DSU operations (finds/unions).
 * - Measures execution time in milliseconds.
 */
public class KruskalMST {

    public static class MSTResult {
        public List<Edge> mstEdges = new ArrayList<>();
        public int totalCost = 0;

        // instrumentation
        public long edgeComparisons = 0; // edges considered (selection step)
        public long dsuFinds = 0;        // number of find() calls recorded in DSU
        public long dsuUnions = 0;      // number of union() merges recorded in DSU

        public double executionTimeMs = 0.0;
    }

    /**
     * Compute MST using Kruskal's algorithm.
     *
     * @param graph the input graph (vertices labeled as strings)
     * @return MSTResult containing the MST edges, cost and instrumentation data
     */
    public static MSTResult computeMST(Graph graph) {
        MSTResult result = new MSTResult();
        long start = System.nanoTime();

        if (graph == null || graph.nodes == null || graph.nodes.isEmpty()) {
            result.executionTimeMs = 0.0;
            return result;
        }

        // Initialize Disjoint Set for all vertices
        DisjointSet dsu = new DisjointSet();
        for (String v : graph.nodes) {
            dsu.makeSet(v);
        }

        // Copy and sort edges by weight (ascending)
        List<Edge> edges = new ArrayList<>(graph.edges);
        edges.sort(Comparator.comparingInt(e -> e.weight));

        // Process edges in sorted order
        for (Edge e : edges) {
            result.edgeComparisons++; // we considered this edge

            String ru = dsu.find(e.from);
            String rv = dsu.find(e.to);

            // defensive: skip edges if vertices are missing
            if (ru == null || rv == null) continue;

            // If endpoints are in different sets -> safe to add edge
            if (!ru.equals(rv)) {
                dsu.union(e.from, e.to);
                result.mstEdges.add(e);
                result.totalCost += e.weight;
            }

            // stop when we have V-1 edges
            if (result.mstEdges.size() == graph.numVertices() - 1) break;
        }

        long end = System.nanoTime();

        // Pull DSU counters into result for reporting
        result.dsuFinds = dsu.findCount;
        result.dsuUnions = dsu.unionCount;
        result.executionTimeMs = (end - start) / 1_000_000.0;

        return result;
    }
}
