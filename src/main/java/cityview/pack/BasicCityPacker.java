package cityview.pack;

import cityview.structure.Block;
import cityview.structure.Building;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Richard on 7/22/2015.
 */
public class BasicCityPacker implements CityPacker {

    public void fitBlockIntoSize(Block block, double maxSizeMetric){
        List<Building> remainingRectangles = new LinkedList<>(block.getBuildings());

        double maxWidthMetric = Math.sqrt(maxSizeMetric);

        double currentDepth = 0;
        BuildingRowPacker rowPacker = new BuildingRowPacker();
        for(ListIterator<Building> iterator = remainingRectangles.listIterator(); iterator.hasNext();){
            Building building = iterator.next();
            iterator.remove();

            List<Building> buildingsInRow = removeBuildingsThatFitIntoWidth(iterator, maxWidthMetric - building.getWidth());
            buildingsInRow.add(0, building);
            rowPacker.resizeBuildings(buildingsInRow, maxWidthMetric);
            rowPacker.arrangeBuildings(buildingsInRow, currentDepth);

            double maxDepth = rowPacker.getMaxDepth(buildingsInRow);
            currentDepth += maxDepth;
        }
    }

    private List<Building> removeBuildingsThatFitIntoWidth(ListIterator<Building> followingRectangles, double maxWidth){
        List<Building> similarShapeRectangles = new LinkedList<>();
        while(followingRectangles.hasNext()){
            Building nextBuilding = followingRectangles.next();

            if(nextBuilding.getWidth() <= maxWidth){
                maxWidth -= nextBuilding.getWidth();
                similarShapeRectangles.add(nextBuilding);
                followingRectangles.remove();
            }else{
                // reset iterator
                followingRectangles.previous();
                break;
            }
        }

        return similarShapeRectangles;
    }

}
