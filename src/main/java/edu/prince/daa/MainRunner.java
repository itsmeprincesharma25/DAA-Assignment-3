package edu.prince.daa;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.nio.file.Path;
import java.util.*;

/**
 * MainRunner
 *
 * This class connects all parts of the MST project.
 * It performs the complete process described in the assignment:
 *  1. Reads graph data from input.json
 *  2. Runs both Prim’s and Kruskal’s algorithms
 *  3. Records results, timings, and operation counts
 *  4. Writes a combined analysis to output.json
 *
 * This is the main class you will run for your final output.
 */
public class MainRunner {

    // File paths (you can adjust if needed)
    private static final String INPUT_PATH = "src/main/resources/input/input.json";
    private static final String OUTPUT_PATH = "src/main/resources/output/output.json";

    public static void main(String[] args) {
        System.out.println("=== DAA Assignment 3: Minimum Spanning Tree (Prim vs Kruskal) ===\n");

        // Step 1: Load graphs from JSON file
        List<Graph> graphs = GraphLoader.loadGraphs(INPUT_PATH);
        if (graphs == null || graphs.isEmpty()) {
            System.err.println("❌ No graphs loaded. Please check input.json path or format.");
            return;
        }

        List<Map<String, Object>> allResults = new ArrayList<>();

        // Step 2: Run both algorithms on each graph
        for (Graph g : graphs) {
            System.out.println("Processing Graph ID: " + g.id + " (" +
                    g.numVertices() + " vertices, " + g.numEdges() + " edges)");

            // 🧱 BONUS: Print the adjacency structure of the graph
            g.printGraph();

            Map<String, Object> graphResult = new LinkedHashMap<>();
            graphResult.put("graph_id", g.id);

            Map<String, Integer> inputStats = new LinkedHashMap<>();
            inputStats.put("vertices", g.numVertices());
            inputStats.put("edges", g.numEdges());
            graphResult.put("input_stats", inputStats);

            // ---- Run Prim’s Algorithm ----
            PrimMST.MSTResult primRes = PrimMST.computeMST(g);
            Map<String, Object> primMap = new LinkedHashMap<>();
            primMap.put("mst_edges", primRes.mstEdges);
            primMap.put("total_cost", primRes.totalCost);
            primMap.put("heap_operations", primRes.heapOperations);
            primMap.put("key_updates", primRes.keyUpdates);
            primMap.put("comparisons", primRes.comparisons);
            primMap.put("execution_time_ms", primRes.executionTimeMs);
            graphResult.put("prim", primMap);

            // ---- Run Kruskal’s Algorithm ----
            KruskalMST.MSTResult kruskalRes = KruskalMST.computeMST(g);
            Map<String, Object> kruskalMap = new LinkedHashMap<>();
            kruskalMap.put("mst_edges", kruskalRes.mstEdges);
            kruskalMap.put("total_cost", kruskalRes.totalCost);
            kruskalMap.put("edge_comparisons", kruskalRes.edgeComparisons);
            kruskalMap.put("dsu_finds", kruskalRes.dsuFinds);
            kruskalMap.put("dsu_unions", kruskalRes.dsuUnions);
            kruskalMap.put("execution_time_ms", kruskalRes.executionTimeMs);
            graphResult.put("kruskal", kruskalMap);

            allResults.add(graphResult);

            // Print quick summary in console
            System.out.printf("  → Prim cost = %d (%.3f ms) | Kruskal cost = %d (%.3f ms)%n%n",
                    primRes.totalCost, primRes.executionTimeMs,
                    kruskalRes.totalCost, kruskalRes.executionTimeMs);
        }

        // Step 3: Write all collected results to output.json
        writeResultsToJson(allResults);
        System.out.println("✅ Results successfully written to: " + OUTPUT_PATH);
    }

    /**
     * Writes a list of results to a formatted JSON file for final analysis.
     */
    private static void writeResultsToJson(List<Map<String, Object>> results) {
        try {
            Path out = Path.of(OUTPUT_PATH);
            out.toFile().getParentFile().mkdirs();

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            try (FileWriter writer = new FileWriter(out.toFile())) {
                gson.toJson(Map.of("results", results), writer);
            }
        } catch (Exception e) {
            System.err.println("⚠️ Error writing output.json: " + e.getMessage());
        }
    }
}
