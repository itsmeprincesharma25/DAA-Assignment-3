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
 * Simple utility to load graphs from a JSON file that matches the assignment format:
 *
 * {
 *   "graphs": [
 *     { "id": 1, "nodes": [...], "edges": [ { "from":"A", "to":"B", "weight":4 }, ... ] },
 *     ...
 *   ]
 * }
 *
 * Usage:
 *   List<Graph> graphs = GraphLoader.loadGraphs("src/main/resources/input/input.json");
 *
 * Returns null and prints an error if file reading or parsing fails.
 */
public class GraphLoader {

    public static List<Graph> loadGraphs(String filePath) {
        try (Reader reader = new FileReader(Path.of(filePath).toFile())) {
            Gson gson = new Gson();
            Type mapType = new TypeToken<Map<String, List<Graph>>>() {}.getType();
            Map<String, List<Graph>> data = gson.fromJson(reader, mapType);
            return data.get("graphs");
        } catch (Exception e) {
            System.err.println("Error loading graphs: " + filePath + " (" + e.getMessage() + ")");
            return null;
        }
    }
}
