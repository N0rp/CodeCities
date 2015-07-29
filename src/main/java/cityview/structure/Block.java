package cityview.structure;

import graph.Leaf;
import graph.Node;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
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
public class Block extends Group implements Structure {

    private String sizeMetricName = Leaf.LINES_OF_CODE;
    private String heightMetricName = Leaf.LINES_OF_CODE;
    private String colorMetricName = Leaf.LINES_OF_CODE;

    private ObjectProperty<Building> hoverBuilding = new SimpleObjectProperty<Building>();
    private ObjectProperty<Building> selectedBuilding = new SimpleObjectProperty<Building>();

    private List<Block> blocks;
    private List<Building> buildings;

    private Box ground;

    private Node node;

    public Block(Node node){
        setGround();
        setData(node);
    }

    private void setGround(){
        ground = new Box();
        ground.setMaterial(new PhongMaterial(Color.GRAY));
        getChildren().addAll(ground);
    }

    private void setData(Node node){
        this.node = node;

        blocks = createBlocks(node.getNodes());
        getChildren().addAll(blocks);

        buildings = createBuildings(node.getLeaves());
        getChildren().addAll(buildings);
    }

    private LinkedList<Block> createBlocks(List<Node> nodes){
        LinkedList<Block> blocks = new LinkedList<>();

        for(int i = 0; i < nodes.size(); i++){
            Node subNode = nodes.get(i);
            Block block = new Block(subNode);
            blocks.add(i, block);
            setBlockListeners(block);
        }

        return blocks;
    }

    private void setBlockListeners(Block block){
        block.hoverBuildingProperty().addListener((observable, oldHoveredBuilding, newHoveredBuilding) -> {handleBuildingHover(newHoveredBuilding);});
    }

    private LinkedList<Building> createBuildings(List<Leaf> leaves){
        LinkedList<Building> buildings = new LinkedList<>();
        for(int i = 0; i < leaves.size(); i++){
            Leaf leaf = leaves.get(i);
            Building building = new Building(leaf);
            buildings.add(i, building);
            setBuildingListeners(building);
        }
        return buildings;
    }

    private void setBuildingListeners(Building building){
        building.isHoverProperty().addListener( (observable, oldValue, newValue) -> {
            if(newValue){
                handleBuildingHover(building);
            }else{
                handleBuildingHover(null);
            }
        });

        building.isSelectedProperty().addListener((observable, oldValue, isSelected) -> {
            if(isSelected) {
                handleBuildingSelected(building);
            }else{
                handleBuildingSelected(null);
            }
        });
    }

    private void handleBuildingHover(Building building){
        hoverBuilding.setValue(building);
    }

    private void handleBuildingSelected(Building selectedBuilding){
        if(this.selectedBuilding.getValue() != null){
            this.selectedBuilding.getValue().setSelected(false);
        }
        this.selectedBuilding.setValue(selectedBuilding);
    }

    @Override
    public String getName() {
        return node.getName();
    }

    public String getSizeMetricName(){
        return this.sizeMetricName;
    }
    public void setSizeMetricName(String sizeMetricName){
        this.sizeMetricName = sizeMetricName;

        for(Block block : blocks){
            block.setSizeMetricName(sizeMetricName);
        }

        for(Building building : buildings){
            building.setSizeMetricName(sizeMetricName);
        }

        ground.setWidth(getLayoutBounds().getWidth());
        ground.setHeight(5);
        ground.setDepth(getLayoutBounds().getDepth());
        ground.setTranslateY(-2.5);
    }

    public String getHeightMetricName(){
        return this.heightMetricName;
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
    public void normalizeHeightMetric(double maxHeightMetric, double maxBuildingHeight){
        for(Block block : blocks){
            block.normalizeHeightMetric(maxHeightMetric, maxBuildingHeight);
        }
        for(Building building : buildings){
            building.normalizeHeightMetric(maxHeightMetric, maxBuildingHeight);
        }
    }

    public String getColorMetricName(){
        return this.colorMetricName;
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
    public void normalizeColorMetric(double maxColorMetric){
        for(Block block : blocks){
            block.normalizeColorMetric(maxColorMetric);
        }
        for(Building building : buildings){
            building.normalizeColorMetric(maxColorMetric);
        }
    }


    public List<Building> getBuildings() {
        return buildings;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public ReadOnlyObjectProperty<Building> hoverBuildingProperty() {
        return hoverBuilding;
    }
    public ReadOnlyObjectProperty<Building> selectedBuildingProperty() {
        return selectedBuilding;
    }


    public double findSumForMetric(String metricName){
        double sumBuildingMetric = 0;
        double sumBlockMetric = 0;

        if(buildings.size() > 0) {
            sumBuildingMetric = buildings.stream().mapToDouble(
                    building -> building.getLeaf().getMetric(metricName))
                    .sum();
        }
        if (blocks.size() > 0) {
            sumBlockMetric = blocks.stream().mapToDouble(subBlock -> subBlock.findSumForMetric(metricName)).sum();
        }

        double sumMetric = sumBlockMetric + sumBuildingMetric;

        return sumMetric;
    }

    public double findMaxForMetric(String metricName){
        double maxBuildingMetric = 0;
        double maxBlockMetric = 0;

        if(buildings.size() > 0) {
            maxBuildingMetric = buildings.stream().mapToDouble(
                    building -> building.getLeaf().getMetric(metricName))
                    .max()
                    .getAsDouble();
        }
        if (blocks.size() > 0) {
            maxBlockMetric = blocks.stream().mapToDouble(subBlock -> subBlock.findMaxForMetric(metricName)).max().getAsDouble();
        }

        double maxMetric = Math.max(maxBuildingMetric, maxBlockMetric);

        return maxMetric;
    }

    public int findTotalBlockCount(){
        int totalBlockCount = 1;
        for(Block block : blocks){
            totalBlockCount += block.findTotalBlockCount();
        }
        return totalBlockCount;
    }

    public int findTotalBuildingCount(){
        int totalBuildingCount = buildings.size();
        for(Block block : blocks){
            totalBuildingCount += block.findTotalBuildingCount();
        }
        return totalBuildingCount;
    }

    public List<Building> findAllBuildings(){
        List<Building> allBuildings = new LinkedList<>();
        for(Block block : blocks){
            allBuildings.addAll(block.findAllBuildings());
        }
        allBuildings.addAll(buildings);
        return allBuildings;
    }

}
