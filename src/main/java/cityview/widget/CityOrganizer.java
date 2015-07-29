package cityview.widget;

import cityview.pack.CityPacker;
import cityview.pack.BasicCityPacker;
import cityview.structure.Block;
import cityview.structure.Building;
import graph.Leaf;
import graph.Node;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.Group;

import java.util.Set;

/**
 * Created by Richard on 7/5/2015.
 */
public class CityOrganizer extends Group{

    private Block rootBlock;

    private final double maxBuildingHeight = 100;

    private Set<String> metricNames;

    public CityOrganizer(Node rootNode){
        setData(rootNode);
    }

    private void setData(Node rootNode){
        this.rootBlock = new Block(rootNode);
        getChildren().addAll(rootBlock);

        this.metricNames = rootNode.findAllMetricNames();
    }

    public void setSizeMetricName(String sizeMetric){
        rootBlock.setSizeMetricName(sizeMetric);
        packCity();
    }

    public void setHeightMetricName(String heightMetric){
        rootBlock.setHeightMetricName(heightMetric);
        normalizeHeightMetric();
    }
    private void normalizeHeightMetric(){
        String heightMetricName = rootBlock.getHeightMetricName();
        double maxHeightMetric = rootBlock.findMaxForMetric(heightMetricName);
        rootBlock.normalizeHeightMetric(maxHeightMetric, maxBuildingHeight);
    }

    public void setColorMetricName(String colorMetric){
        rootBlock.setColorMetricName(colorMetric);
        normalizeColorMetric();
    }
    private void normalizeColorMetric(){
        String colorMetricName = rootBlock.getColorMetricName();
        double maxColorMetric = rootBlock.findMaxForMetric(colorMetricName);
        rootBlock.normalizeColorMetric(maxColorMetric);
    }

    private void packCity(){
        CityPacker packer = new BasicCityPacker();
        double blockSize = rootBlock.findSumForMetric(rootBlock.getSizeMetricName());
        packer.fitBlockIntoSize(rootBlock, blockSize);
        int foo = 5;
    }

    public Set<String> getMetricNames(){
        return metricNames;
    }

    public Block getRootBlock() {
        return rootBlock;
    }

    public ReadOnlyObjectProperty<Building> hoverBuildingProperty() {
        return rootBlock.hoverBuildingProperty();
    }

    public ReadOnlyObjectProperty<Building> selectedBuildingProperty() {
        return rootBlock.selectedBuildingProperty();
    }
}
