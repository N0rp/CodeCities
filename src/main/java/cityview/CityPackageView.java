package cityview;

import city.CityBuilding;
import city.CityPackage;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by Richard on 7/6/2015.
 */
public class CityPackageView extends Group{


    public CityBuildingView[] getBuildings() {
        return buildings;
    }

    public CityPackageView[] getSubPackages() {
        return subPackages;
    }

    private CityPackageView[] subPackages;
    private CityBuildingView[] buildings;

    public CityPackageView(CityPackage cityPackage){
        setData(cityPackage);
    }

    public void setCityBundle(CityBundle cityBundle){
        double packageZ = 0;
        for(CityPackageView subPack : subPackages){
            Rectangle bounds = subPack.getBounds(cityBundle);
            subPack.setCityBundle(cityBundle);
            subPack.setTranslateZ(packageZ);
            packageZ += bounds.getHeight();
        }

        double buildingX = 0;
        for(CityBuildingView building : buildings){
            building.setCityBundle(cityBundle);
            building.setTranslateX(buildingX);
            building.setTranslateZ(packageZ);
            buildingX += cityBundle.getMaxBuildingSize();
        }
    }

    private void setData(CityPackage cityPackage){
        subPackages = new CityPackageView[cityPackage.getSubPackages().size()];

        for(int i = 0; i < cityPackage.getSubPackages().size(); i++){
            CityPackage subPackage = cityPackage.getSubPackages().get(i);
            subPackages[i] = new CityPackageView(subPackage);
        }
        getChildren().addAll(subPackages);

        buildings = new CityBuildingView[cityPackage.getBuildings().length];
        for(int i = 0; i < cityPackage.getBuildings().length; i++){
            CityBuilding cityBuilding = cityPackage.getBuildings()[i];
            buildings[i] = new CityBuildingView(cityBuilding);
        }
        getChildren().addAll(buildings);
    }

    public Rectangle getBounds(CityBundle cityBundle){
        Rectangle bounds = new Rectangle(0, 0);

        int buildingSize = cityBundle.getMaxBuildingSize() * buildings.length;
        bounds.setWidth(buildingSize);
        bounds.setHeight(buildingSize);

        for(CityPackageView subPackage : subPackages){
            Rectangle subBounds = subPackage.getBounds(cityBundle);
            unionBounds(bounds, subBounds);
        }

        return bounds;
    }

    public Rectangle unionBounds(Rectangle bounds, Rectangle subBounds){
        if(subBounds.getWidth() > bounds.getWidth()){
            bounds.setWidth(subBounds.getWidth());
        }
        bounds.setHeight(bounds.getHeight() + subBounds.getHeight());
        return bounds;
    }

    public int getMaxLoc(){
        int maxLoc = 0;
        int maxBuildingLoc = 0;
        int maxPackageLoc = 0;

        if(buildings.length > 0) {
            maxBuildingLoc = Arrays.stream(buildings).mapToInt(
                    cityBuilding -> cityBuilding.getCityBuilding()
                            .getLoc())
                    .max()
                    .getAsInt();
        }
        if(subPackages.length > 0) {
            maxPackageLoc = Arrays.stream(subPackages).mapToInt(subPackage -> subPackage.getMaxLoc()).max().getAsInt();
        }

        maxLoc = Math.max(maxBuildingLoc, maxPackageLoc);

        return maxLoc;
    }

    public int getMaxMcc(){
        int maxMcc = 0;
        int maxBuildingMcc = 0;
        int maxPackageMcc = 0;

        if(buildings.length > 0) {
            maxBuildingMcc = Arrays.stream(buildings).mapToInt(cityBuilding -> cityBuilding.getCityBuilding().getMcccabe()).max().getAsInt();
        }
        if(subPackages.length > 0) {
            maxPackageMcc = Arrays.stream(subPackages).mapToInt(subPackage -> subPackage.getMaxMcc()).max().getAsInt();
        }

        maxMcc = Math.max(maxBuildingMcc, maxPackageMcc);

        return maxMcc;
    }

    public int getMaxNl(){
        int maxNl = 0;
        int maxBuildingNl = 0;
        int maxPackageNl = 0;

        if(buildings.length > 0) {
            maxBuildingNl = Arrays.stream(buildings).mapToInt(cityBuilding -> cityBuilding.getCityBuilding().getNl()).max().getAsInt();
        }
        if(subPackages.length > 0) {
            maxPackageNl = Arrays.stream(subPackages).mapToInt(subPackage -> subPackage.getMaxNl()).max().getAsInt();
        }

        maxNl = Math.max(maxBuildingNl, maxPackageNl);

        return maxNl;
    }
}
