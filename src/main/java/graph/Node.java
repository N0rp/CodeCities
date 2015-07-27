package graph;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Richard on 7/5/2015.
 */
public class Node {

    public String getName() {
        return name;
    }

    public List<Leaf> getBuildings() {
        return buildings;
    }

    public List<Node> getSubPackages() {
        return subPackages;
    }

    private String name;
    private List<Leaf> buildings;
    private List<Node> subPackages;

    public Node(String packageName, Leaf... buildings){
        this(packageName, Arrays.asList(buildings));
    }

    public Node(String packageName, List<Leaf> buildings){
        this.name = packageName;
        this.buildings = buildings;
        this.subPackages = new LinkedList<>();
    }

    public void addSubPackage(Node pack){
        this.subPackages.add(pack);
    }
}
