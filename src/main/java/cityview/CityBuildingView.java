package cityview;

import city.CityBuilding;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

/**
 * Created by Richard on 7/6/2015.
 */
public class CityBuildingView extends Box{


    public CityBuilding getCityBuilding() {
        return cityBuilding;
    }

    private CityBuilding cityBuilding;
    private CityBundle cityBundle;

    public CityBuildingView(CityBuilding cityBuilding){
        this.cityBuilding = cityBuilding;

        setOnMouseEntered(me -> {
            setMaterial(new PhongMaterial(Color.AQUA));
        });
        setOnMouseExited(me -> {
            refreshBuildingColor();
        });
    }

    public void setCityBundle(CityBundle bundle){
        this.cityBundle = bundle;
        double size = cityBuilding.getLoc() / (double)bundle.getMaxBuildingLoc() * bundle.getMaxBuildingSize();
        double height = cityBuilding.getMcccabe() / (double) bundle.getMaxBuildingMcc() * bundle.getMaxBuildingHeight();
        setWidth(size);
        setDepth(size);
        setHeight(height);
        setMaterial(new PhongMaterial(getBuildingColor(cityBuilding.getNl(), bundle.getMaxBuildingNl())));
        setTranslateY(height / 2);
    }

    private void refreshBuildingColor(){
        setMaterial(new PhongMaterial(getBuildingColor(cityBuilding.getNl(), cityBundle.getMaxBuildingNl())));
    }

    private Color getBuildingColor(double value, double max){
        double middleStart = max / 3;
        double middleEnd = middleStart * 2;
        if(value < middleStart){
            return Color.GREEN;
        }else if(value < middleEnd){
            return Color.ORANGE;
        }else{
            return Color.RED;
        }
    }

}
