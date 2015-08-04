package cityview.pack;

import cityview.structure.Building;
import cityview.structure.Structure;

import java.util.List;

/**
 * Created by Richard on 7/26/2015.
 */
public class RowPacker {

    private List<Building> buildingsInRow;
    private double depth;
    private double rowWidth;
    private double rowZ;

    public RowPacker(List<Building> buildingsInRow, double rowWidth, double rowZ){
        this.buildingsInRow = buildingsInRow;
        this.rowWidth = rowWidth;
        this.rowZ = rowZ;

        this.depth = calculateMaxDepth();
    }

    public double getRowDepth(){
        return depth;
    }

    private double calculateMaxDepth(){
        Building maxDepthStructure = buildingsInRow.stream().max((buildingA, buildingB) -> Double.compare(buildingA.getStructureDepth(), buildingB.getStructureDepth())).get();
        return maxDepthStructure.getStructureDepth();
    }

    public void resizeBuildings(){
        resizeToMaxBuildingHeight();
        resizeToContainerWidth();
    }

    public void arrangeBuildings(){
        double buildingX = 0;
        for(Building building : buildingsInRow){
            building.setTranslateX(building.getWidth()/2 + buildingX);
            building.setTranslateZ(building.getDepth()/2 + rowZ);

            buildingX += building.getWidth();
        }
    }

    private void resizeToMaxBuildingHeight(){
        for(Building building : buildingsInRow){
            building.resizeToDepth(depth);
        }
    }

    private void resizeToContainerWidth(){
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
            double relativeWidth = building.getWidth() / currentWidth;
            double newWidth = relativeWidth * rowWidth;
            building.resizeToWidth(newWidth);
        }
    }
}
