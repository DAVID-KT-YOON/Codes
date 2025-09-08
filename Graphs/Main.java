package Graphs;
public class Main {
    public static void main(String[] args) {
        Dijkstra dijkstra = new Dijkstra();
        dijkstra.insert('a', 'b',3);
        dijkstra.insert('a', 'c',7);
        dijkstra.insert('b', 'c',2);
        dijkstra.insert('a', 'd',10);
        dijkstra.insert('d', 'c',2);
        dijkstra.insert('d', 'e',5);
        dijkstra.insert('c', 'e',1);
        // graph.graphTraversal1('a');
        // graph.remove('c');
        DijkstraResults results = dijkstra.performDijkstra('a');
        System.out.println(results.distance );
        System.out.println(results.previous);
    }
}
