package Framework;

import org.osbot.rs07.script.MethodProvider;

import java.util.*;

public class NodeManager {
    static MethodProvider api;
    private Node lastNode = null;
    private List<NodeObject> nodes = new ArrayList<>();


    public static Node EMPTY = new Node(api){
        @Override
        public int execute() throws InterruptedException{
            System.out.print("We couldn't find any node to execute right now");
            return 0;
        }

        @Override
        public boolean canExecute(){
            return true;
        }
    };

    private Comparator<NodeObject> comparator = (one, two) ->Integer.compare(two.getPriority(this), one.getPriority(this));

    public Node getLastNode(){
        return lastNode;
    }

    public void addNode(Node node){
        nodes.add(new NodeObject(node));
    }

    public Node getNextNode() throws InterruptedException {
        Collections.sort(nodes, comparator);
        for(NodeObject nodeObject : nodes){
            if(nodeObject.getNode().canExecute()){
                lastNode = nodeObject.getNode();
                return nodeObject.getNode();
            }
        }
        return EMPTY;
    }

    class NodeObject{
        private int defaultPriority;
        private Map<Class<? extends Node>, Integer> priorityMappings = new HashMap<>();
        private Node referencedNode;
        public NodeObject(Node obj){
            referencedNode = obj;
            Class<? extends Node> referencedNodeClass = referencedNode.getClass();

            PrioritisedNode nodeAnnotation = referencedNodeClass.getAnnotation(PrioritisedNode.class);
            //defaultPriority = nodeAnnotation.priority().getPriority();
            defaultPriority = nodeAnnotation.priority().getPriority();

            if(referencedNodeClass.isAnnotationPresent(After.class)) {
                After priority = referencedNodeClass.getAnnotation(After.class);
                priorityMappings.put(priority.node(), priority.priority().getPriority());
            }
        }

        public Node getNode(){
            return referencedNode;
        }

        public int getPriority(NodeManager nodeManager){
            return nodeManager.getLastNode() == null ? defaultPriority : priorityMappings.getOrDefault(nodeManager.getLastNode().getClass(),defaultPriority);
        }
    }
}
