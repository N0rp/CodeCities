package city;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

import java.util.Arrays;

/**
 * Created by Richard on 7/5/2015.
 */
public class CityDrawer {

    private CityPackage pack;

    private int maxLoc;
    private int maxMcCabe;
    private int maxNl;

    private int maxBuildingSize = 100;
    private int getMaxBuildingHeight = 100;

    public CityDrawer(CityPackage pack){
        this.pack = pack;
        calculateCity();
    }

    private void calculateCity(){
        this.maxLoc = Arrays.stream(pack.getBuildings()).mapToInt(cityBuilding -> cityBuilding.getLoc()).max().getAsInt();
        this.maxMcCabe = Arrays.stream(pack.getBuildings()).mapToInt(cityBuilding -> cityBuilding.getMcccabe()).max().getAsInt();
        this.maxNl = Arrays.stream(pack.getBuildings()).mapToInt(cityBuilding -> cityBuilding.getNl()).max().getAsInt();
    }

    public void drawPackage(Group root){
        double buildingX = 0;
        double buildingY = 0;
        for(CityBuilding building : pack.getBuildings()){
            Point2D upperLeftCorner = new Point2D(buildingX, buildingY);
            Point2D bottomRightCorner = drawBuilding(root, building, upperLeftCorner);
            buildingX += bottomRightCorner.getX();
            buildingY += bottomRightCorner.getY();
        }
    }

    /**
     * Draw building.
     * @param root
     * @param building
     * @param translate
     * @return bottom right corner of drawn building
     */
    private Point2D drawBuilding(Group root, CityBuilding building, Point2D translate){
        double size = ((double)building.getLoc()) / maxLoc * maxBuildingSize;
        double height = building.getMcccabe() / maxMcCabe * getMaxBuildingHeight;
        Box testBox = new Box(size, height, size);
        testBox.setMaterial(new PhongMaterial(getNlColor(building.getNl())));
        testBox.setTranslateX(translate.getX());
        testBox.setTranslateY(translate.getY());
        root.getChildren().addAll(testBox);

        Point2D bottomRightCorner = new Point2D(testBox.getLayoutX() + testBox.getWidth(), testBox.getLayoutY() + testBox.getDepth());
        return bottomRightCorner;
    }

    private Color getNlColor(double nl){
        int middleStart = maxNl / 3;
        int middleEnd = middleStart * 2;
        if(nl < middleStart){
            return Color.GREEN;
        }else if(nl < middleEnd){
            return Color.ORANGE;
        }else{
            return Color.RED;
        }
    }


}
