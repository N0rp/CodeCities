package cityview;

import graph.Leaf;
import graph.MockNodeFactory;
import graph.Node;
import javafx.scene.shape.Rectangle;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Richard on 7/22/2015.
 */
public class BasicCityPackerTest {

    @Test
    public void testArrangeBlock_OneBuildingInOneRow_Success() {
        /*
            A
         */
        Leaf leafA =  new Leaf("LeafA", MockNodeFactory.createMetricMap(100, 100, 20));

        Leaf[] leaves = new Leaf[]{leafA};
        Node node = new Node("NodeAlpha", Arrays.asList(leaves));
        Block block = new Block(node);

        CityPacker cityPacker = new BasicCityPacker();
        cityPacker.arrangeBlock(block, 100);

        assertIsSquare(block);
    }

    public void testArrangeBlock_TwoBuildingsInOneRow_Success() {
        /*
            A, B
         */
        Leaf leafA =  new Leaf("LeafA", MockNodeFactory.createMetricMap(100, 100, 20));
        Leaf leafB =  new Leaf("LeafA", MockNodeFactory.createMetricMap(100, 100, 20));

        Leaf[] leaves = new Leaf[]{leafA, leafB};
        Node node = new Node("NodeAlpha", Arrays.asList(leaves));
        Block block = new Block(node);

        CityPacker cityPacker = new BasicCityPacker();
        cityPacker.arrangeBlock(block, 200);

        assertIsSquare(block);
    }

    public void testArrangeBlock_TwoBuildingsInTwoRows_Success() {
        /*
            A
            B
         */
        Leaf leafA =  new Leaf("LeafA", MockNodeFactory.createMetricMap(100, 100, 20));
        Leaf leafB =  new Leaf("LeafA", MockNodeFactory.createMetricMap(100, 100, 20));

        Leaf[] leaves = new Leaf[]{leafA, leafB};
        Node node = new Node("NodeAlpha", Arrays.asList(leaves));
        Block block = new Block(node);

        CityPacker cityPacker = new BasicCityPacker();
        cityPacker.arrangeBlock(block, 100);

        assertIsSquare(block);
    }

    private void assertIsSquare(Block block){
        Rectangle bounds = getBuildingBounds(block.getBuildings());
        Assert.assertEquals((int)bounds.getWidth(), (int)bounds.getHeight());
    }

     private Rectangle getBuildingBounds(List<Building> buildings){
         Rectangle bounds = new Rectangle();
         for(Building building : buildings){
             if(building.getLayoutX() + building.getWidth() > bounds.getWidth()){
                 bounds.setWidth(building.getLayoutX() + building.getWidth());
             }
             if(building.getLayoutY() + building.getDepth() > bounds.getHeight()){
                 bounds.setHeight(building.getLayoutY() + building.getDepth());
             }
         }

         return bounds;
     }

}
