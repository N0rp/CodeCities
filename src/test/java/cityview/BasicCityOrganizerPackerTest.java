package cityview;

import cityview.pack.BasicCityPacker;
import cityview.pack.CityPacker;
import cityview.structure.Block;
import graph.Leaf;
import graph.MockNodeFactory;
import graph.Node;
import org.junit.Test;

import java.util.Arrays;

import static cityview.CityBoundsUtil.*;

/**
 * Created by Richard on 7/22/2015.
 */
public class BasicCityOrganizerPackerTest {

    @Test
    public void testArrangeBlock_OneBuildingInOneRow_Success() {
        /*
            A
         */
        Leaf leafA =  new Leaf("LeafA", MockNodeFactory.createMetricMap(100, 100, 20));

        Leaf[] leaves = new Leaf[]{leafA};
        Node node = new Node("NodeAlpha", Arrays.asList(leaves));
        Block block = new Block(node);

        CityPacker cityPacker = new BasicCityPacker(block, 0);
        cityPacker.fitBlock();

        assertIsSquare(block);
    }

    @Test
    public void testArrangeBlock_TwoBuildingsInOneRow_Success() {
        /*
            A, B
         */
        Leaf leafA =  new Leaf("LeafA", MockNodeFactory.createMetricMap(100, 100, 20));
        Leaf leafB =  new Leaf("LeafA", MockNodeFactory.createMetricMap(100, 100, 20));

        Leaf[] leaves = new Leaf[]{leafA, leafB};
        Node node = new Node("NodeAlpha", Arrays.asList(leaves));
        Block block = new Block(node);

        CityPacker cityPacker = new BasicCityPacker(block, 0);
        cityPacker.fitBlock();

        assertIsSquare(block);
    }

    @Test
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

        CityPacker cityPacker = new BasicCityPacker(block, 0);
        cityPacker.fitBlock();

        assertIsRectangle(block, 10, 20);
    }

}
