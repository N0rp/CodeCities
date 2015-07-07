package cityview;

import city.CityBuilding;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.text.Text;

/**
 * Created by Richard on 7/6/2015.
 */
public class CityBuildingView extends Group{



    public CityBuilding getCityBuilding() {
        return cityBuilding;
    }

    private Box buildingBox;
    private Text buildingLabel;

    private CityBuilding cityBuilding;
    private CityBundle cityBundle;

    public CityBuildingView(CityBuilding cityBuilding){
        createView();
        setData(cityBuilding);
    }

    private void createView(){
        this.buildingBox = new Box();
        this.buildingLabel = new Text("test");

        setOnMouseEntered(me -> {
            this.buildingBox.setMaterial(new PhongMaterial(Color.AQUA));
            buildingLabel.setVisible(true);
        });
        setOnMouseExited(me -> {
            refreshBuildingColor();
            buildingLabel.setVisible(false);
        });

        getChildren().addAll(buildingBox, buildingLabel);
    }

    private void setData(CityBuilding cityBuilding){
        this.cityBuilding = cityBuilding;
        this.buildingLabel.setText(cityBuilding.getName());
    }

    public void setCityBundle(CityBundle bundle){
        this.cityBundle = bundle;
        double size = cityBuilding.getLoc() / (double)bundle.getMaxBuildingLoc() * bundle.getMaxBuildingSize();
        double height = cityBuilding.getMcccabe() / (double) bundle.getMaxBuildingMcc() * bundle.getMaxBuildingHeight();
        this.buildingBox.setWidth(size);
        this.buildingBox.setDepth(size);
        this.buildingBox.setHeight(height);
        this.buildingBox.setMaterial(new PhongMaterial(getBuildingColor(cityBuilding.getNl(), bundle.getMaxBuildingNl())));
        setTranslateY(height / 2);

        buildingLabel.setTranslateX(-buildingLabel.getWrappingWidth() / 2);
        buildingLabel.setTranslateY(height / 2 + buildingLabel.getFont().getSize());
        buildingLabel.setRotate(180);
        buildingLabel.setVisible(false);
    }

    private void refreshBuildingColor(){
        this.buildingBox.setMaterial(new PhongMaterial(getBuildingColor(cityBuilding.getNl(), cityBundle.getMaxBuildingNl())));
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
