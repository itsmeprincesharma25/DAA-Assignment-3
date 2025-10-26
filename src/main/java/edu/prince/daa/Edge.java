package edu.prince.daa;

/**
 * Edge
 * Simple data class representing an undirected weighted edge between two vertices.
 *
 * Fields are public for simplicity (easy JSON mapping and direct access).
 * This fits the assignment needs: store edges from input and present MST edges in output.
 */
public class Edge {
    public String from;
    public String to;
    public int weight;

    public Edge() { }

    public Edge(String from, String to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return String.format("%s - %s (%d)", from, to, weight);
    }
}
