package graph;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Richard on 7/5/2015.
 */
public class MockNodeFactory {

    public static Map<String, Double> createMetricMap(double loc, double mcc, double nl){
        Map<String, Double> metricMap = new HashMap<>();
        metricMap.put(Leaf.LINES_OF_CODE, loc);
        metricMap.put(Leaf.MC_CABE, mcc);
        metricMap.put(Leaf.NESTING_LEVEL, nl);

        return metricMap;
    }

    public static Node getSimplePackage(){
        Leaf building = new Leaf("foo", createMetricMap(700, 100, 0));
        Leaf building2 = new Leaf("bar", createMetricMap(400, 50, 50));
        Leaf building3 = new Leaf("gaa", createMetricMap(1000, 10, 100));
        Node root = new Node("Root", building, building2, building3);
        return root;
    }

    public static Node getSimpleDoublePackage(){
        Leaf buildingA1 = new Leaf("foo", createMetricMap(700, 100, 0));
        Leaf buildingA2 = new Leaf("bar", createMetricMap(400, 50, 50));
        Leaf buildingA3 = new Leaf("gaa", createMetricMap(1000, 10, 100));
        Node packA = new Node("GroupA", buildingA1, buildingA2, buildingA3);

        Leaf buildingB1 = new Leaf("foo", createMetricMap(700, 200, 100));
        Leaf buildingB2 = new Leaf("bar", createMetricMap(400, 70, 0));
        Leaf buildingB3 = new Leaf("gaa", createMetricMap(1000, 30, 50));
        Node packB = new Node("GroupB", buildingB1, buildingB2, buildingB3);


        Node root = new Node("Root");
        root.addSubNodes(packA);
        root.addSubNodes(packB);
        return root;
    }

    public static Node getMediumSizePackage(){
        Leaf buildingA1 = new Leaf("Level A 1", createMetricMap(700, 100, 0));
        Leaf buildingA2 = new Leaf("Level A 2", createMetricMap(400, 50, 50));
        Leaf buildingA3 = new Leaf("Level A 3", createMetricMap(1000, 10, 100));
        Node subPackA = new Node("Level A", buildingA1, buildingA2, buildingA3);

        Leaf buildingB1 = new Leaf("Level B 1", createMetricMap(100, 100, 10));
        Leaf buildingB2 = new Leaf("Level B 2", createMetricMap(300, 200, 20));
        Node subPackB = new Node("Level B", buildingB1, buildingB2);

        Leaf buildingRoot1 = new Leaf("RootBuilding", createMetricMap(900, 100, 50));
        Node rootPackage = new Node("Root", buildingRoot1);

        subPackA.addSubNodes(subPackB);
        rootPackage.addSubNodes(subPackA);

        return rootPackage;
    }
}
