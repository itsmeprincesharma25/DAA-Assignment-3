package edu.prince.daa;

import java.util.*;

/**
 * PrimMST
 *
 * Implements Prim's algorithm to compute the Minimum Spanning Tree (MST)
 * for a weighted undirected graph.
 *
 * Approach (simple to explain):
 * - Build adjacency lists for fast neighbor access.
 * - Use a PriorityQueue (min-heap) of candidate edges (lazy decrease-key by re-inserting).
 * - Repeatedly extract the cheapest candidate; add its vertex to MST if not already added.
 *
 * Instrumentation (required by assignment):
 * - mstEdges: edges that form the MST
 * - totalCost: sum of MST edge weights
 * - heapOperations: count of PQ inserts + polls
 * - keyUpdates: times when we improved the best known connection for a vertex
 * - comparisons: neighbor weight comparisons
 * - executionTimeMs: runtime in milliseconds
 */
public class PrimMST {

    public static class MSTResult {
        public List<Edge> mstEdges = new ArrayList<>();
        public int totalCost = 0;
        public long heapOperations = 0;
        public long keyUpdates = 0;
        public long comparisons = 0;
        public double executionTimeMs = 0.0;
    }

    // Priority queue node representing a candidate connection to 'vertex'
    private static class PQNode {
        final String vertex;
        final int weight;      // key: weight to connect this vertex
        final String parent;   // parent vertex in MST (null for root)

        PQNode(String vertex, int weight, String parent) {
            this.vertex = vertex;
            this.weight = weight;
            this.parent = parent;
        }
    }

    /**
     * Compute MST using Prim's algorithm.
     *
     * @param graph input Graph with nodes and edges
     * @return MSTResult containing MST edges, cost and instrumentation
     */
    public static MSTResult computeMST(Graph graph) {
        MSTResult result = new MSTResult();
        long start = System.nanoTime();

        if (graph == null || graph.nodes == null || graph.nodes.isEmpty()) {
            result.executionTimeMs = 0.0;
            return result;
        }

        // Build adjacency list (undirected)
        Map<String, List<Edge>> adj = new HashMap<>();
        for (String v : graph.nodes) adj.put(v, new ArrayList<>());
        for (Edge e : graph.edges) {
            // defensive: only add if vertices exist in nodes list
            if (adj.containsKey(e.from)) adj.get(e.from).add(e);
            if (adj.containsKey(e.to)) adj.get(e.to).add(new Edge(e.to, e.from, e.weight));
        }

        // Start from the first vertex in the list
        String startVertex = graph.nodes.get(0);

        // Min-heap ordered by weight
        PriorityQueue<PQNode> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.weight));
        Map<String, Integer> bestKey = new HashMap<>(); // best known weight to connect vertex
        Set<String> inMST = new HashSet<>();

        // Initialize keys
        for (String v : graph.nodes) bestKey.put(v, Integer.MAX_VALUE);
        bestKey.put(startVertex, 0);

        // Insert start vertex (key 0)
        pq.add(new PQNode(startVertex, 0, null));
        result.heapOperations++;

        while (!pq.isEmpty() && inMST.size() < graph.numVertices()) {
            PQNode node = pq.poll();
            result.heapOperations++;

            String u = node.vertex;
            // Skip stale entries
            if (inMST.contains(u)) continue;

            // Add vertex to MST and record edge (if not root)
            inMST.add(u);
            if (node.parent != null) {
                result.mstEdges.add(new Edge(node.parent, u, node.weight));
                result.totalCost += node.weight;
            }

            // Relax neighbors of u
            for (Edge e : adj.getOrDefault(u, Collections.emptyList())) {
                String v = e.to;
                if (inMST.contains(v)) continue;

                result.comparisons++;
                int currentBest = bestKey.getOrDefault(v, Integer.MAX_VALUE);
                if (e.weight < currentBest) {
                    // found a better connection for v
                    bestKey.put(v, e.weight);
                    pq.add(new PQNode(v, e.weight, u)); // lazy decrease-key
                    result.heapOperations++;
                    result.keyUpdates++;
                }
            }
        }

        long end = System.nanoTime();
        result.executionTimeMs = (end - start) / 1_000_000.0;
        return result;
    }
}
