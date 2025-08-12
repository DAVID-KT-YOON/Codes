package Graphs;
public class Edge implements Comparable<Edge>{
    private final Node to;
    private int value;


    public Edge(Node to, int value){
        this.to = to;
        this.value = value;
    }
    public Edge(Node to){
        this.to = to;
    }
    public Node getTo(){
        return this.to;
    }
    public int getValue(){
        return this.value;
    }
    @Override
    public int compareTo(Edge other){
        return Integer.compare(this.value, other.value);

    }
}
    