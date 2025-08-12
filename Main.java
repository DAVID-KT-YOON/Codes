public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.insert('a', 'b');
        graph.insert('a', 'c');
        graph.insert('b', 'c');
        graph.insert('a', 'd');
        graph.insert('d', 'c');
        graph.insert('d', 'e');
        graph.insert('c', 'e');
        graph.graphTraversal1('a');
        // graph.remove('c');
        System.out.println(graph);
    }
}
