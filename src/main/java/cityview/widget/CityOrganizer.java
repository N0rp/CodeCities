package cityview.widget;

import cityview.pack.CityPacker;
import cityview.pack.BasicCityPacker;
import cityview.structure.Block;
import cityview.structure.Building;
import graph.Leaf;
import graph.Node;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Group;

import java.util.Set;

/**
 * Created by Richard on 7/5/2015.
 */
public class CityOrganizer extends Group{

    private Block rootBlock;

    private final String defaultSizeMetric = Leaf.LINES_OF_CODE;
    private final String defaultHeightMetric = Leaf.LINES_OF_CODE;
    private final String defaultColorMetric = Leaf.LINES_OF_CODE;

    private final double maxBuildingHeight = 100;

    public ObjectProperty<Building> hoverBuildingProperty() {
        return rootBlock.hoverBuildingProperty();
    }

    public ObjectProperty<Building> selectedBuildingProperty() {
        return rootBlock.selectedBuildingProperty();
    }

    private Set<String> metricNames;

    public CityOrganizer(Node rootNode){
        setData(rootNode);
    }

    private void setData(Node rootNode){
        this.rootBlock = new Block(rootNode);
        getChildren().addAll(rootBlock);

        this.metricNames = rootNode.findAllMetricNames();

        rootBlock.setSizeMetricName(defaultSizeMetric);
        rootBlock.setHeightMetricName(defaultHeightMetric);
        rootBlock.setColorMetricName(defaultColorMetric);

        normalizeHeightMetric();
        normalizeColorMetric();

        CityPacker packer = new BasicCityPacker();
        double blockSize = rootBlock.getSumMetric(defaultSizeMetric);
        packer.fitBlockIntoSize(rootBlock, blockSize);
        int foo = 5;
    }

    private void normalizeHeightMetric(){
        String heightMetricName = rootBlock.getHeightMetricName();
        double maxHeightMetric = rootBlock.getMaxMetric(heightMetricName);
        rootBlock.normalizeHeightMetric(maxHeightMetric, maxBuildingHeight);
    }

    private void normalizeColorMetric(){
        String colorMetricName = rootBlock.getColorMetricName();
        double maxColorMetric = rootBlock.getMaxMetric(colorMetricName);
        rootBlock.normalizeColorMetric(maxColorMetric);
    }

    public void setSizeMetricName(String sizeMetric){
        rootBlock.setSizeMetricName(sizeMetric);
    }

    public void setHeightMetricName(String heightMetric){
        rootBlock.setHeightMetricName(heightMetric);
        normalizeHeightMetric();
    }

    public void setColorMetricName(String colorMetric){
        rootBlock.setColorMetricName(colorMetric);
        normalizeColorMetric();
    }

    public Set<String> getMetricNames(){
        return metricNames;
    }

    public Block getRootBlock() {
        return rootBlock;
    }
}