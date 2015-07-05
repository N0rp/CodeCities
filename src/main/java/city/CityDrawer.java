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
    private Point2D drawBuilding(Group root, final CityBuilding building, Point2D translate){
        double size = ((double)building.getLoc()) / maxLoc * maxBuildingSize;
        double height = ((double)building.getMcccabe()) / maxMcCabe * getMaxBuildingHeight;
        Color boxColor = getNlColor(building.getNl());

        Box testBox = new Box(size, height, size);
        testBox.setMaterial(new PhongMaterial(boxColor));
        testBox.setTranslateX(translate.getX());
        testBox.setTranslateY(testBox.getHeight() / 2);
        //testBox.setTranslateY(translate.getY());
        root.getChildren().addAll(testBox);

        testBox.setOnMouseEntered(me -> {
            testBox.setMaterial(new PhongMaterial(Color.AQUA));
        });
        testBox.setOnMouseExited(me -> {
            testBox.setMaterial(new PhongMaterial(getNlColor(building.getNl())));
        });

        Point2D bottomRightCorner = new Point2D(translate.getX() + size, translate.getY() + size);
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
