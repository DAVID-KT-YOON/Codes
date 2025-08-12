package Graphs;
public class Node {
    private char ch;

        public Node(char ch){
            this.ch = ch;
        }
        public char getCh(){
            return this.ch;
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
        @Override
        public String toString(){
            return this.ch + " ";
        }
}
