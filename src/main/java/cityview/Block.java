package cityview;

import graph.Leaf;
import graph.Node;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

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

    public List<Block> getBlocks() {
        return blocks;
    }


    public Building getSelectedBuilding() {
        return selectedBuilding.get();
    }

    public ObjectProperty<Building> selectedBuildingProperty() {
        return selectedBuilding;
    }

    private ObjectProperty<Building> selectedBuilding = new SimpleObjectProperty<Building>();

    private List<Block> blocks;
    private List<Building> buildings;

    private Box ground;

    public Block(Node node){
        ground = new Box();
        ground.setMaterial(new PhongMaterial(Color.GRAY));
        getChildren().addAll(ground);
        setData(node);
    }

    private void setData(Node node){
        blocks = new LinkedList<>();

        for(int i = 0; i < node.getNodes().size(); i++){
            Node subNode = node.getNodes().get(i);
            Block block = new Block(subNode);
            blocks.add(i, block);

            block.selectedBuildingProperty().addListener((observable, oldHoveredBuilding, newHoveredBuilding) -> {handleBuildingHover(newHoveredBuilding);});
        }
        getChildren().addAll(blocks);

        buildings = new LinkedList<>();
        for(int i = 0; i < node.getLeaves().size(); i++){
            Leaf leaf = node.getLeaves().get(i);
            Building building = new Building(leaf);
            buildings.add(i, building);

            building.isHoverProperty().addListener( (observable, oldValue, newValue) -> {
                handleBuildingHover(building, newValue);
            });
        }
        getChildren().addAll(buildings);
    }

    private void handleBuildingHover(Building building){
        selectedBuilding.setValue(building);
    }

    private void handleBuildingHover(Building building, boolean isHover){
        if(isHover){
            handleBuildingHover(building);
        }else{
            handleBuildingHover(null);
        }
    }

    public void setSizeMetricName(String sizeMetricName){
        this.sizeMetricName = sizeMetricName;

        for(Block block : blocks){
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

        for(Block block : blocks){
            block.setHeightMetricName(heightMetricName);
        }

        for(Building building : buildings){
            building.setHeightMetricName(heightMetricName);
        }
    }

    public String getHeightMetricName(){
        return this.heightMetricName;
    }

    public void setColorMetricName(String colorMetricName){
        this.colorMetricName = colorMetricName;

        for(Block block : blocks){
            block.setColorMetricName(colorMetricName);
        }

        for(Building building : buildings){
            building.setColorMetricName(colorMetricName);
        }
    }

    public String getColorMetricName(){
        return this.colorMetricName;
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
        if (blocks.size() > 0) {
            maxBlockMetric = blocks.stream().mapToDouble(subPackage -> subPackage.getMaxMetric(metricName)).max().getAsDouble();
        }

        maxMetric = Math.max(maxBuildingMetric, maxBlockMetric);

        return maxMetric;
    }

    public void normalizeHeightMetric(double maxHeightMetric, double maxBuildingHeight){
        for(Block block : blocks){
            block.normalizeHeightMetric(maxHeightMetric, maxBuildingHeight);
        }
        for(Building building : buildings){
            building.normalizeHeightMetric(maxHeightMetric, maxBuildingHeight);
        }
    }

    public void normalizeColorMetric(double maxColorMetric){
        for(Block block : blocks){
            block.normalizeColorMetric(maxColorMetric);
        }
        for(Building building : buildings){
            building.normalizeColorMetric(maxColorMetric);
        }
    }
}
