package cityview;

import cityview.structure.Block;
import graph.Leaf;
import graph.MockNodeFactory;
import graph.Node;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;
import static cityview.CityBoundsUtil.*;

/**
 * Created by Richard on 7/28/2015.
 */
public class BlockTest {

    @Test
     public void testSetSizeMetricName_OneBuildingInOneBlock_Success() {
        /*
            {A}
         */
        Leaf leafA =  new Leaf("LeafA", MockNodeFactory.createMetricMap(100, 100, 20));

        Leaf[] leaves = new Leaf[]{leafA};
        Node node = new Node("NodeAlpha", Arrays.asList(leaves));

        Block block = new Block(node);
        block.setSizeMetricName(Leaf.LINES_OF_CODES);

        assertEquals(100, (int) block.findMaxForMetric(Leaf.LINES_OF_CODES));
        assertEquals(100, (int)block.findSumForMetric(Leaf.LINES_OF_CODES));
        assertIsSquare(block);
    }

    @Test
    public void testSetSizeMetricName_TwoBuildingsInOneBlock_Success() {
        /*
            {A, B}
         */
        Leaf leafA =  new Leaf("LeafA", MockNodeFactory.createMetricMap(100, 100, 20));
        Leaf leafB =  new Leaf("LeafA", MockNodeFactory.createMetricMap(100, 100, 20));

        Leaf[] leaves = new Leaf[]{leafA, leafB};
        Node node = new Node("NodeAlpha", Arrays.asList(leaves));
        Block block = new Block(node);
        block.setSizeMetricName(Leaf.LINES_OF_CODES);

        assertEquals(100, (int) block.findMaxForMetric(Leaf.LINES_OF_CODES));
        assertEquals(200, (int) block.findSumForMetric(Leaf.LINES_OF_CODES));
        assertIsSquare(block);
    }

    @Test
    public void testSetSizeMetricName_ThreeBuildingsInOneBlock_Success() {
        /*
            {A, B, C}
         */
        Leaf leafA =  new Leaf("LeafA", MockNodeFactory.createMetricMap(50, 100, 20));
        Leaf leafB =  new Leaf("LeafB", MockNodeFactory.createMetricMap(50, 100, 20));
        Leaf leafC =  new Leaf("LeafC", MockNodeFactory.createMetricMap(100, 100, 20));

        Leaf[] leaves = new Leaf[]{leafA, leafB, leafC};
        Node node = new Node("NodeAlpha", Arrays.asList(leaves));
        Block block = new Block(node);
        block.setSizeMetricName(Leaf.LINES_OF_CODES);

        assertEquals(100, (int) block.findMaxForMetric(Leaf.LINES_OF_CODES));
        assertEquals(200, (int) block.findSumForMetric(Leaf.LINES_OF_CODES));
        assertIsSquare(block);
    }

    @Test
    public void testSetSizeMetricName_TwoBuildingsInTwoBlocks_Success() {
        /*
            {{A},{B}}
         */
        Leaf leafA1 =  new Leaf("LeafA1", MockNodeFactory.createMetricMap(100, 100, 20));
        Leaf leafB2 =  new Leaf("LeafB1", MockNodeFactory.createMetricMap(10, 100, 20));

        Leaf[] leavesA = new Leaf[]{leafA1};
        Leaf[] leavesB = new Leaf[]{leafB2};
        Node nodeA = new Node("NodeAlpha", Arrays.asList(leavesA));
        Node nodeB = new Node("NodeBeta", Arrays.asList(leavesB));

        Node rootNote = new Node("RootNode");
        rootNote.addSubNodes(nodeA, nodeB);

        Block block = new Block(rootNote);
        block.setSizeMetricName(Leaf.LINES_OF_CODES);

        assertEquals(3, block.findTotalBlockCount());
        assertEquals(2, block.findTotalBuildingCount());
        assertEquals(100, (int) block.findMaxForMetric(Leaf.LINES_OF_CODES));
        assertEquals(110, (int) block.findSumForMetric(Leaf.LINES_OF_CODES));

        assertIsSquare(block);
    }

    @Test
    public void testSetSizeMetricName_ThreeBuildingsInTwoBlocks_Success() {
        /*
            {{A},{B},C}
         */
        Leaf leafA1 =  new Leaf("LeafA1", MockNodeFactory.createMetricMap(10, 100, 20));
        Leaf leafB2 =  new Leaf("LeafB1", MockNodeFactory.createMetricMap(50, 100, 20));
        Leaf leafRoot =  new Leaf("LeafRoot", MockNodeFactory.createMetricMap(100, 100, 20));

        Leaf[] leavesA = new Leaf[]{leafA1};
        Leaf[] leavesB = new Leaf[]{leafB2};
        Node nodeA = new Node("NodeAlpha", Arrays.asList(leavesA));
        Node nodeB = new Node("NodeBeta", Arrays.asList(leavesB));

        Node rootNote = new Node("RootNode", leafRoot);
        rootNote.addSubNodes(nodeA, nodeB);

        Block block = new Block(rootNote);
        block.setSizeMetricName(Leaf.LINES_OF_CODES);

        assertEquals(3, block.findTotalBlockCount());
        assertEquals(3, block.findTotalBuildingCount());
        assertEquals(100, (int) block.findMaxForMetric(Leaf.LINES_OF_CODES));
        assertEquals(160, (int) block.findSumForMetric(Leaf.LINES_OF_CODES));

        assertIsSquare(block);
    }

    @Test
    public void testSetSizeMetricName_TwoBuildingsPerBlock_Success() {
        /*
            {{A},{B},C}
         */
        Leaf leafA1 =  new Leaf("LeafA1", MockNodeFactory.createMetricMap(10, 100, 20));
        Leaf leafA2 =  new Leaf("LeafA2", MockNodeFactory.createMetricMap(10, 100, 20));
        Leaf leafB1 =  new Leaf("LeafB1", MockNodeFactory.createMetricMap(60, 100, 20));
        Leaf leafB2 =  new Leaf("LeafB2", MockNodeFactory.createMetricMap(40, 100, 20));
        Leaf leafRoot1 =  new Leaf("LeafRoot1", MockNodeFactory.createMetricMap(100, 100, 20));
        Leaf leafRoot2 =  new Leaf("LeafRoot2", MockNodeFactory.createMetricMap(200, 100, 20));

        Node nodeA = new Node("NodeAlpha", Arrays.asList(leafA1, leafA2));
        Node nodeB = new Node("NodeBeta", Arrays.asList(leafB1, leafB2));

        Node rootNote = new Node("RootNode", leafRoot1, leafRoot2);
        rootNote.addSubNodes(nodeA, nodeB);

        Block block = new Block(rootNote);
        block.setSizeMetricName(Leaf.LINES_OF_CODES);

        assertEquals(3, block.findTotalBlockCount());
        assertEquals(6, block.findTotalBuildingCount());
        assertEquals(200, (int) block.findMaxForMetric(Leaf.LINES_OF_CODES));
        assertEquals(420, (int) block.findSumForMetric(Leaf.LINES_OF_CODES));

        assertIsSquare(block);
    }

}
