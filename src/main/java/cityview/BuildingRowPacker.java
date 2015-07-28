package cityview;

import java.util.List;

/**
 * Created by Richard on 7/26/2015.
 */
public class BuildingRowPacker {

    public double getMaxDepth(List<Building> buildingsInRow){
        Building maxDepthBuilding = buildingsInRow.stream().max((buildingA, buildingB) -> Double.compare(buildingA.getDepth(), buildingB.getDepth())).get();
        return maxDepthBuilding.getDepth();
    }

    public void arrangeAndResizeBuildings(List<Building> buildingsInRow, double rowZ, double maxWidth){
        arrangeBuildings(buildingsInRow, rowZ);
        resizeToMaxBuildingHeight(buildingsInRow);
        resizeToContainerWidth(buildingsInRow, maxWidth);
    }

    private void arrangeBuildings(List<Building> buildingsInRow, double rowZ){
        double buildingX = 0;
        for(Building building : buildingsInRow){
            building.setTranslateX(buildingX);
            building.setTranslateZ(rowZ);

            buildingX += building.getWidth();
        }
    }

    private void resizeToMaxBuildingHeight(List<Building> buildingsInRow){
        double maxDepth = getMaxDepth(buildingsInRow);
        for(Building building : buildingsInRow){
            building.resizeToDepth(maxDepth);
        }
    }

    private void resizeToContainerWidth(List<Building> buildingsInRow, double rowWidth){
        double currentWidth = 0;
        Building previousBuilding = null;
        for(Building building : buildingsInRow){
            if(previousBuilding != null && building.getLayoutX() < previousBuilding.getLayoutX()){
                System.err.println("The rectangle "+building+" does not come after the previous "+previousBuilding);
            }

            previousBuilding = building;
            currentWidth += building.getWidth();
        }

        double currentX = 0;
        for(Building building : buildingsInRow){
            building.setLayoutX(currentX);
            double relativeWidth = building.getWidth() / currentWidth;
            double newWidth = relativeWidth * rowWidth;
            building.resizeToWidth(newWidth);
            currentX += newWidth;
        }
    }
}
