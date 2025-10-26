package edu.prince.daa;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * GraphLoader
 * - Loads graphs from JSON and ensures adjacency lists are built.
 */
public class GraphLoader {

    public static List<Graph> loadGraphs(String filePath) {
        try (Reader reader = new FileReader(Path.of(filePath).toFile())) {
            Gson gson = new Gson();
            Type mapType = new TypeToken<Map<String, List<Graph>>>() {}.getType();
            Map<String, List<Graph>> data = gson.fromJson(reader, mapType);
            List<Graph> graphs = data.get("graphs");

            // Ensure adjacency lists are built for each graph (Gson does not call custom constructors)
            if (graphs != null) {
                for (Graph g : graphs) {
                    if (g != null) g.buildAdjacencyList();
                }
            }
            return graphs;
        } catch (Exception e) {
            System.err.println("GraphLoader.loadGraphs: error reading file '" + filePath + "': " + e.getMessage());
            return null;
        }
    }
}
