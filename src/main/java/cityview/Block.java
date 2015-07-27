package cityview;

import graph.Leaf;
import graph.Node;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Rectangle;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Richard on 7/6/2015.
 */
public class Block extends Group{

    private String sizeMetricName = Leaf.LINES_OF_CODE;
    private String heightMetricName = Leaf.LINES_OF_CODE;
    private String colorMetricName = Leaf.LINES_OF_CODE;

    public List<Building> getBuildings() {
        return buildings;
    }

    public List<Block> getSubPackages() {
        return subPackages;
    }

    private List<Block> subPackages;
    private List<Building> buildings;

    private Box ground;

    public Block(Node node){
        ground = new Box();
        ground.setMaterial(new PhongMaterial(Color.GRAY));
        getChildren().addAll(ground);
        setData(node);
    }

    private void setData(Node node){
        subPackages = new LinkedList<>();

        for(int i = 0; i < node.getSubPackages().size(); i++){
            Node subPackage = node.getSubPackages().get(i);
            subPackages.add(i, new Block(subPackage));
        }
        getChildren().addAll(subPackages);

        buildings = new LinkedList<>();
        for(int i = 0; i < node.getBuildings().size(); i++){
            Leaf leaf = node.getBuildings().get(i);
            buildings.add(i, new Building(leaf));
        }
        getChildren().addAll(buildings);
    }

    public void setSizeMetricName(String sizeMetricName){
        this.sizeMetricName = sizeMetricName;

        for(Block block : subPackages){
            block.setSizeMetricName(sizeMetricName);
        }

        for(Building building : buildings){
            building.setSizeMetricName(sizeMetricName);
        }

        double maxSize = getMaxMetric(sizeMetricName);
        ground.setWidth(maxSize);
        ground.setHeight(maxSize);
        ground.setDepth(5);
    }

    public void setHeightMetricName(String heightMetricName){
        this.heightMetricName = heightMetricName;

        for(Block block : subPackages){
            block.setHeightMetricName(heightMetricName);
        }

        for(Building building : buildings){
            building.setHeightMetricName(heightMetricName);
        }
    }

    public void setColorMetricName(String colorMetricName){
        this.colorMetricName = colorMetricName;

        for(Block block : subPackages){
            block.setColorMetricName(colorMetricName);
        }

        for(Building building : buildings){
            building.setColorMetricName(colorMetricName);
        }
    }

    public double getMaxMetric(String metricName){
        double maxMetric = 0;
        double maxBuildingMetric = 0;
        double maxBlockMetric = 0;

        if(buildings.size() > 0) {
            maxBuildingMetric = buildings.stream().mapToDouble(
                    cityBuilding -> cityBuilding.getLeaf().getMetric(metricName))
                    .max()
                    .getAsDouble();
        }
        if (subPackages.size() > 0) {
            maxBlockMetric = subPackages.stream().mapToDouble(subPackage -> subPackage.getMaxMetric(metricName)).max().getAsDouble();
        }

        maxMetric = Math.max(maxBuildingMetric, maxBlockMetric);

        return maxMetric;
    }

    public void normalizeHeightMetric(double maxHeightMetric, double maxBuildingHeight){
        for(Block block : subPackages){
            block.normalizeHeightMetric(maxHeightMetric, maxBuildingHeight);
        }
        for(Building building : buildings){
            building.normalizeHeightMetric(maxHeightMetric, maxBuildingHeight);
        }
    }

    public void normalizeColorMetric(double maxColorMetric){
        for(Block block : subPackages){
            block.normalizeColorMetric(maxColorMetric);
        }
        for(Building building : buildings){
            building.normalizeColorMetric(maxColorMetric);
        }
    }
}
