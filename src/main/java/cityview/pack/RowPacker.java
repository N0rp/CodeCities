package cityview.pack;

import cityview.structure.Building;
import cityview.structure.Structure;

import java.util.List;

/**
 * Created by Richard on 7/26/2015.
 */
public class RowPacker {

    private List<Building> buildingsInRow;
    private double rowWidth;
    private double rowZ;

    private double depth;

    RowPacker(RowPackerFactory rowPackerFactory){
        this.buildingsInRow = rowPackerFactory.buildingsInRow.getFittingStructures();
        this.rowWidth = rowPackerFactory.rowWidth;
        this.rowZ = rowPackerFactory.rowZ;

        this.depth = calculateMaxDepth(buildingsInRow);
    }

    public double getRowDepth(){
        return depth;
    }

    private double calculateMaxDepth(List<Building> buildingsInRow){
        double maxDepth = 0;
        if(buildingsInRow.size() > 0) {
            Building maxDepthStructure = buildingsInRow.stream().max((buildingA, buildingB) -> Double.compare(buildingA.getStructureDepth(), buildingB.getStructureDepth())).get();
            maxDepth = maxDepthStructure.getStructureDepth();
        }
        return maxDepth;
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
