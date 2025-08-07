import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;


public class Graph {
    Map<Node,List<Node>> graph;
    public Graph(){
        graph = new HashMap<>();
    }

    public void insert(char ch1, char ch2){
        Node node = new Node(ch1);
        Node node2 = new Node(ch2);
        if (graph.get(node) == null){
            List<Node> list = new ArrayList<>();
            list.add(node2);
            graph.putIfAbsent(node, list);
        }
        else if(!graph.get(node).contains(node2)){
            graph.get(node).add(node2);
        }
    }
    public void delete(char ch1){
        Node node = new Node(ch1);
        graph.remove(node);
        for(List<Node> list : graph.values()){
            list.remove(node);
        }
    }
    
    public String toString(){
        StringBuilder stringbuilder = new StringBuilder();
        for(Node key : graph.keySet()){
            stringbuilder.append(key.ch);
            stringbuilder.append(" => ");
            for(Node value : graph.get(key)){
                stringbuilder.append(value.ch + " ");
            }
            stringbuilder.append("\n");
        }
        return stringbuilder.toString();
    }
    private class Node{
        private char ch;

        public Node(char ch){
            this.ch = ch;
        }
        @Override
        public boolean equals(Object o){
            if(this == o) 
                return true;
            if(!(o instanceof Node))
                return false;
            Node node = (Node)o;
            return ch == node.ch;

        }
        @Override
        public int hashCode(){
            return Character.hashCode(ch);
        }
    }

}
