import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class Graph {

    private Map<Node,List<Node>> graph;
    

    public Graph(){
        graph = new HashMap<>();
    }


    public void insert(char ch1, char ch2){
        insert(new Node(ch1), new Node(ch2));
    }
    private void insert(Node node, Node target){

        graph.computeIfAbsent(node, k-> new ArrayList<>());

        if(!graph.get(node).contains(target)){
            graph.get(node).add(target);
        }
        graph.computeIfAbsent(target, k-> new ArrayList<>());
        if(!graph.get(target).contains(node)){
            graph.get(target).add(node);
        }
    }


    public void remove(char ch1){
        remove(new Node(ch1));
    }
    private void remove(Node target){
        graph.remove(target);
        for(List<Node> list : graph.values()){
            list.remove(target);
        }
    }

    //DFS add null pointer exception
    public void graphTraversal1(char ch){
        graphTraversal1(new Node(ch));
    }
    private void graphTraversal1(Node start){
        Stack<Node> stack = new Stack<>();
        Set<Node> visited = new HashSet<>();
        
        stack.push(start);
        
        while(!stack.isEmpty()){
            Node node = stack.pop();
            
            if (!visited.contains(node)){
                visited.add(node);
                /*act here */
                System.out.println(node.getCh());

                for(Node neighbor : graph.get(node)){
                    if(!visited.contains(neighbor))
                        stack.push(neighbor);
                }
            }

        }
    }
    //DFS recursive
    public void graphTraversal1Rec(char ch){

        Set<Node> visited = new HashSet<>();
        graphTraversal1Rec(new Node(ch), visited);
    }
    private void graphTraversal1Rec(Node start, Set<Node> visited){
        if(start == null || visited.contains(start)){
            return;
        }
        visited.add(start);
        //perform a task
        for(Node neighbor : graph.get(start)){
            graphTraversal1Rec(neighbor, visited);
        }
    }

    //BFS add null pointer exception
    public void graphTraversal2(char ch){
        graphTraversal2(new Node(ch));
    }
    private void graphTraversal2(Node start){
        Queue<Node> queue = new LinkedList<>();
        Set<Node> visited = new HashSet<>();
        
        queue.add(start);
        while(!queue.isEmpty()){
            Node node = queue.remove();
            
            if (!visited.contains(node)){
                visited.add(node);
                /*act here */
                for(Node neighbor : graph.get(node)){
                    if(!visited.contains(neighbor))
                        queue.add(neighbor);
                }
            }

        }
    }

    public String toString(){
        StringBuilder stringbuilder = new StringBuilder();
        for(Node key : graph.keySet()){
            stringbuilder.append(key.getCh());
            stringbuilder.append(" => ");
            for(Node value : graph.get(key)){
                stringbuilder.append(value.getCh()+ " ");
            }
            stringbuilder.append("\n");
        }
        return stringbuilder.toString();
    }



}
