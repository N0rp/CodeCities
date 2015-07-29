package cityview;

import cityview.pack.BuildingRowPacker;
import cityview.structure.Building;
import graph.Leaf;
import graph.MockNodeFactory;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * Created by Richard on 7/22/2015.
 */
public class BuildingRowPackerTest {

    @Test
    public void testArrangeAndResizeBuildings_OneBuildingNoResize_Success() {
        final double maxWidth = 10;
        Leaf leafA =  new Leaf("LeafA", MockNodeFactory.createMetricMap(10 * 10, 100, 20));
        List<Building> buildings = new LinkedList<>();
        buildings.add(new Building(leafA));

        BuildingRowPacker rowPacker = new BuildingRowPacker();
        rowPacker.arrangeBuildings(buildings, 0);
        rowPacker.resizeBuildings(buildings, maxWidth);

        assertEquals(0, (int)buildings.get(0).getLayoutX());
        assertEquals(0, (int) buildings.get(0).getLayoutY());
        assertEquals(10, (int)buildings.get(0).getWidth());
        assertEquals(10, (int)buildings.get(0).getDepth());

        assertMaxWidth(maxWidth, buildings);
    }

    @Test
    public void testArrangeAndResizeBuildings_OneBuildingWithResize_Success() {
        final double maxWidth = 20;
        Leaf leafA =  new Leaf("LeafA", MockNodeFactory.createMetricMap(10 * 10, 100, 20));
        List<Building> buildings = new LinkedList<>();
        buildings.add(new Building(leafA));

        BuildingRowPacker rowPacker = new BuildingRowPacker();
        rowPacker.arrangeBuildings(buildings, 0);
        rowPacker.resizeBuildings(buildings, maxWidth);

        assertEquals(0, (int)buildings.get(0).getLayoutX());
        assertEquals(0, (int) buildings.get(0).getLayoutY());
        assertEquals((int) maxWidth, (int)buildings.get(0).getWidth());
        assertEquals(5, (int)buildings.get(0).getDepth());

        assertMaxWidth(maxWidth, buildings);
    }

    @Test
    public void testArrangeAndResizeBuildings_TwoBuildingsWithResize_Success() {
        final double maxWidth = 15;
        Leaf leafA =  new Leaf("LeafA", MockNodeFactory.createMetricMap(100, 100, 20));
        Leaf leafB =  new Leaf("LeafB", MockNodeFactory.createMetricMap(50, 50, 20));
        List<Building> buildings = new LinkedList<>();
        buildings.add(new Building(leafA));
        buildings.add(new Building(leafB));

        BuildingRowPacker rowPacker = new BuildingRowPacker();
        rowPacker.arrangeBuildings(buildings, 0);
        rowPacker.resizeBuildings(buildings, maxWidth);

        assertEquals(0, (int)buildings.get(0).getLayoutX());
        assertEquals(0, (int) buildings.get(0).getLayoutY());
        assertEquals(10, (int)buildings.get(0).getWidth());
        assertEquals(10, (int)buildings.get(0).getDepth());

        assertEquals(10, (int)buildings.get(1).getLayoutX());
        assertEquals(0, (int) buildings.get(1).getLayoutY());
        assertEquals(5, (int)buildings.get(1).getWidth());
        assertEquals(10, (int)buildings.get(1).getDepth());

        assertMaxWidth(maxWidth, buildings);
    }


    private void assertMaxWidth(double maxWidth, List<Building> buildings){
        double width = 0;
        Building previousBuilding = null;
        for(Building building : buildings){
            if(previousBuilding != null){
                assertEquals(-1, Double.compare(previousBuilding.getLayoutX(), building.getLayoutX()));
            }
            previousBuilding = building;

            width = building.getLayoutX() + building.getWidth();
        }
        assertEquals((int)maxWidth, (int)width);
    }

}
