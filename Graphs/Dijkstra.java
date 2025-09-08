package Graphs;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
/*
 * directed Dijkstras
 */
public class Dijkstra{
    private Map<Node,List<Edge>> graph;

    public Dijkstra(){
        this.graph = new HashMap<>();
    }

    public void insert(char from, char to, int weight){
        if(weight < 0){
            return;
        }
        insert(new Node(from),new Edge(new Node(to),weight));
    }
    public void insert(Node from, Edge to){
        graph.computeIfAbsent(from, k-> new ArrayList<>());
        if (!graph.get(from).contains(to)){
            graph.get(from).add(to);
        }
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (Node key : graph.keySet()){
            sb.append(key.getCh());
            sb.append(" => ");
            for(Edge value : graph.get(key)){
                sb.append(value.getTo().getCh()+ " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    public DijkstraResults performDijkstra(char startNode){
        return performDijkstra(new Node(startNode));
    }

    private DijkstraResults performDijkstra(Node startNode){
        Map<Node, Integer> distance = new HashMap<>();
        Map<Node, Node> previous = new HashMap<>();
        Set<Node> visited = new HashSet<>();

        for (Node n : graph.keySet()) {
            distance.put(n, Integer.MAX_VALUE); // ∞
            previous.put(n, null);
        }
        distance.put(startNode, 0);

        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.add(new Edge(startNode, 0));
        
        while(!pq.isEmpty()){
            Edge current = pq.poll();
            Node node = current.getTo();
            
            if(visited.contains(node)){
                continue;
            }
            visited.add(node);
            
            for (Edge edge : graph.getOrDefault(node, Collections.emptyList())){
                Node node2 = edge.getTo();
                int value = current.getValue() + edge.getValue();

                if(value < distance.getOrDefault(node2, Integer.MAX_VALUE)){
                    distance.replace(node2,value);
                    previous.replace(node2,node);
                    pq.add(new Edge(node2, value));
                } 
            }
        }
        return new DijkstraResults(distance, previous);
    }
    public List<Edge> performPrims(Node curr){
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        Set<Node> visited = new HashSet<>();
        List<Edge> list = new ArrayList<>();

        pq.addAll(graph.get(curr));
        visited.add(curr);

        while(visited.size() < graph.keySet().size()){
            Edge minEdge = pq.remove();
            Node nextNode = minEdge.getTo();
            if(visited.contains( nextNode )){
                continue;
            }
            visited.add( nextNode );
            list.add(minEdge);

            for(Edge edge : graph.get(nextNode)){
                if(!visited.contains(edge.getTo())){
                    pq.add(edge);
                }
            }
        }
        return list;
    }

}