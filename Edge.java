public class Edge{
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
}
    