package graph;

import cityview.Building;

import java.util.*;

/**
 * Created by Richard on 7/5/2015.
 */
public class Node {

    public String getName() {
        return name;
    }

    public List<Leaf> getLeaves() {
        return leaves;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    private String name;
    private List<Leaf> leaves;
    private List<Node> nodes;

    public Node(String packageName, Leaf... buildings){
        this(packageName, Arrays.asList(buildings));
    }

    public Node(String packageName, List<Leaf> leaves){
        this.name = packageName;
        this.leaves = leaves;
        this.nodes = new LinkedList<>();
    }

    // Find costs more computing power than a simple get.
    // That's why we use the prefix find on the Node and get on the Leaf
    public Set<String> findAllMetricNames(){
        Set<String> metricNames = new HashSet<>();

        for(Node node : nodes){
            Set<String> nodeMetricNames = node.findAllMetricNames();
            metricNames.addAll(nodeMetricNames);
        }

        for(Leaf leaf : leaves){
            Set<String> leafMetricNames = leaf.getAllMetricNames();
            metricNames.addAll(leafMetricNames);
        }

        return metricNames;
    }

    public void addSubPackage(Node node){
        this.nodes.add(node);
    }
}
