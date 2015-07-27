package cityview;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Richard on 7/22/2015.
 */
public class BasicCityPacker implements CityPacker{

    public void arrangeBlock(Block block, double maxSizeMetric){
        List<Building> remainingRectangles = new LinkedList<>(block.getBuildings());

        double maxWidthMetric = Math.sqrt(maxSizeMetric);

        double currentHeight = 0;
        for(ListIterator<Building> iterator = remainingRectangles.listIterator(); iterator.hasNext();){
            Building building = iterator.next();
            iterator.remove();

            List<Building> buildingsInRow = removeBuildingsThatFitIntoWidth(iterator, maxWidthMetric - building.getWidth());
            buildingsInRow.add(0, building);
            BuildingRowPacker rowPacker = new BuildingRowPacker();
            rowPacker.arrangeAndResizeBuildings(buildingsInRow, currentHeight, maxWidthMetric);

            double maxHeight = rowPacker.getMaxDepth(buildingsInRow);
            currentHeight += maxHeight;
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
