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
    }

    private void calculateCity(){
        if(pack.getBuildings().length > 0) {
            this.maxLoc = Arrays.stream(pack.getBuildings()).mapToInt(cityBuilding -> cityBuilding.getLoc()).max().getAsInt();
            this.maxMcCabe = Arrays.stream(pack.getBuildings()).mapToInt(cityBuilding -> cityBuilding.getMcccabe()).max().getAsInt();
            this.maxNl = Arrays.stream(pack.getBuildings()).mapToInt(cityBuilding -> cityBuilding.getNl()).max().getAsInt();
        }
    }

    public void drawPackages(Group root){
        /*
        Idea for a simple organization:
        Each building in a package is organized horizontally.
        Each subpackage is organized below the root
        Each root is organized horizontally
         */
        double packageX = 0;
        double packageY = 0;
        for(CityPackage subPack : pack.getSubPackages()){
            Point2D upperLeftCorner = new Point2D(packageX, packageY);
            Point2D bottomRightCorner = drawBuildings(root, subPack, upperLeftCorner);
            packageX += bottomRightCorner.getX();
            packageY += bottomRightCorner.getY();
        }
    }

    private void drawSingleSubPackage(Group root){
        double packageX = 0;
        double packageY = 0;
        for(CityPackage subPack : pack.getSubPackages()){
            Point2D upperLeftCorner = new Point2D(packageX, packageY);
            Point2D bottomRightCorner = drawBuildings(root, subPack, upperLeftCorner);
            packageX += bottomRightCorner.getX();
            packageY += bottomRightCorner.getY();
        }
    }

    /**
     * Draw building.
     * @param root
     * @param pack
     * @param upperLeftPackCorner
     * @return bottom right corner of drawn building
     */
    private Point2D drawBuildings(Group root, CityPackage pack, Point2D upperLeftPackCorner){
        calculateCity();
        double buildingX = upperLeftPackCorner.getX();
        double buildingY = upperLeftPackCorner.getY();
        for(CityBuilding building : pack.getBuildings()){
            Point2D upperLeftCorner = new Point2D(buildingX, buildingY);
            Point2D bottomRightCorner = drawSingleBuilding(root, building, upperLeftCorner);
            buildingX += bottomRightCorner.getX();
            buildingY += bottomRightCorner.getY();
        }
        Point2D bottomRightPackCorner = new Point2D(buildingX, buildingY);
        return bottomRightPackCorner;
    }

    /**
     * Draw building.
     * @param root
     * @param building
     * @param translate
     * @return bottom right corner of drawn building
     */
    private Point2D drawSingleBuilding(Group root, final CityBuilding building, Point2D translate){
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
