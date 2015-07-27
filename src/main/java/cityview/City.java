package cityview;

import graph.Leaf;
import graph.Node;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Richard on 7/5/2015.
 */
public class City extends Group{

    private Block rootBlock;

    private String sizeMetric = Leaf.LINES_OF_CODE;
    private String heightMetric = Leaf.LINES_OF_CODE;
    private String colorMetric = Leaf.LINES_OF_CODE;

    private final double maxBuildingHeight = 100;

    public City(Node rootNode){
        setData(rootNode);
    }

    private void setData(Node rootNode){
        this.rootBlock = new Block(rootNode);
        getChildren().addAll(rootBlock);
        rootBlock.setSizeMetricName(sizeMetric);
        rootBlock.setHeightMetricName(heightMetric);
        rootBlock.setColorMetricName(colorMetric);

        normalizeHeightMetric(heightMetric);
        normalizeColorMetric(colorMetric);

        CityPacker packer = new BasicCityPacker();
        double blockSize = rootBlock.getMaxMetric(sizeMetric);
        packer.arrangeBlock(rootBlock, blockSize);
    }

    private void normalizeHeightMetric(String heightMetricName){
        double maxHeightMetric = rootBlock.getMaxMetric(heightMetricName);
        rootBlock.normalizeHeightMetric(maxHeightMetric, maxBuildingHeight);
    }

    private void normalizeColorMetric(String colorMetricName){
        double maxColorMetric = rootBlock.getMaxMetric(colorMetricName);
        rootBlock.normalizeColorMetric(maxColorMetric);
    }
}
