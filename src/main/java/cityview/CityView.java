package cityview;

import city.CityBuilding;
import city.CityPackage;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Rectangle;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Richard on 7/5/2015.
 */
public class CityView extends Group{

    private CityPackageView[] rootPackages;

    private int maxLoc;
    private int maxMcCabe;
    private int maxNl;

    private int maxBuildingSize = 100;
    private int getMaxBuildingHeight = 100;

    public CityView(CityPackage[] rootPackages){
        setData(rootPackages);
    }

    private void setData(CityPackage[] rootPackages){
        this.rootPackages = new CityPackageView[rootPackages.length];
        for(int i = 0; i < rootPackages.length; i++){
            this.rootPackages[i] = new CityPackageView(rootPackages[i]);
        }
    }

    public void setCityBundle(CityBundle cityBundle){
        double packageX = 0;
        for(CityPackageView rootPack : rootPackages){
            Rectangle bounds = rootPack.getBounds(cityBundle);
            rootPack.setCityBundle(cityBundle);
            rootPack.setTranslateX(packageX);
            packageX += bounds.getWidth();
        }
        getChildren().addAll(rootPackages);
    }



    private Node testDraw(Rectangle bounds){
        Box testBox = new Box(bounds.getWidth(), 10, bounds.getHeight());
        testBox.setMaterial(new PhongMaterial(Color.BLACK));
        testBox.setTranslateY(testBox.getHeight() / 2);
        return testBox;
    }

    public CityBundle createCityBundle(){
        int[] maxPackageLoc = new int[rootPackages.length];
        int[] maxPackageMcc = new int[rootPackages.length];
        int[] maxPackageNl = new int[rootPackages.length];
        for(int i = 0; i < rootPackages.length; i++){
            maxPackageLoc[i] = rootPackages[i].getMaxLoc();
            maxPackageMcc[i] = rootPackages[i].getMaxMcc();
            maxPackageNl[i] = rootPackages[i].getMaxNl();
        }

        int maxLoc = 0;
        int maxMcc = 0;
        int maxNl = 0;
        if(rootPackages.length > 0){
            maxLoc = Arrays.stream(maxPackageLoc).max().getAsInt();
            maxMcc = Arrays.stream(maxPackageMcc).max().getAsInt();
            maxNl = Arrays.stream(maxPackageNl).max().getAsInt();
        }

        CityBundle cityBundle = new CityBundle();
        cityBundle.setMaxBuildingLoc(maxLoc);
        cityBundle.setMaxBuildingMcc(maxMcc);
        cityBundle.setMaxBuildingNl(maxNl);

        cityBundle.setMaxBuildingHeight(100);
        cityBundle.setMaxBuildingSize(100);

        return cityBundle;
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
