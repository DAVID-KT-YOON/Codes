package Graphs;

import java.util.Map;

public class DijkstraResults {
    public final Map<Node, Integer> distance;
    public final Map<Node, Node> previous;

    public DijkstraResults(Map<Node, Integer> distance, Map<Node, Node> previous) {
        this.distance = distance;
        this.previous = previous;
    }
}
