package cityview.widget;

import cityview.structure.Building;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

/**
 * Created by Richard on 7/28/2015.
 */
public class CityOverlay extends VBox {

    private CityOrganizer cityOrganizer;

    @FXML
    private ChoiceBox<String> sizeMetric;

    @FXML
    private ChoiceBox<String> heightMetric;

    @FXML
    private ChoiceBox<String> colorMetric;

    @FXML
    private Text buildingName;

    @FXML
    private Text buildingSize;

    @FXML
    private Text buildingHeight;

    @FXML
    private Text buildingColor;

    public CityOverlay(CityOrganizer cityOrganizer){
        this.cityOrganizer = cityOrganizer;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/core/CityOverlay.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }


    @FXML
    private void initialize() {
        initializeSizeChoiceBox();
        initializeHeightChoiceBox();
        initializeColorChoiceBox();

        initializeHoveredBuilding();
        initializeSelectedBuilding();
    }

    private void initializeSizeChoiceBox(){
        sizeMetric.setItems(FXCollections.observableArrayList(cityOrganizer.getMetricNames()));
        sizeMetric.getSelectionModel().selectedItemProperty().addListener((observable, oldItem, newItem) -> {
            if(newItem != null && newItem.length() > 0){
                cityOrganizer.setSizeMetricName(newItem);
            }
        });
        sizeMetric.getSelectionModel().selectFirst();
    }

    private void initializeHeightChoiceBox(){
        heightMetric.setItems(FXCollections.observableArrayList(cityOrganizer.getMetricNames()));
        heightMetric.getSelectionModel().selectedItemProperty().addListener((observable, oldItem, newItem) -> {
            if(newItem != null && newItem.length() > 0){
                cityOrganizer.setHeightMetricName(newItem);
            }
        });
        heightMetric.getSelectionModel().selectFirst();
    }

    private void initializeColorChoiceBox(){
        colorMetric.setItems(FXCollections.observableArrayList(cityOrganizer.getMetricNames()));
        colorMetric.getSelectionModel().selectedItemProperty().addListener((observable, oldItem, newItem) -> {
            if(newItem != null && newItem.length() > 0){
                cityOrganizer.setColorMetricName(newItem);
            }
        });
        colorMetric.getSelectionModel().selectFirst();
    }

    private void initializeHoveredBuilding(){
        cityOrganizer.hoverBuildingProperty().addListener((observable, oldBuilding, newBuilding) -> {
            refreshBuildingText();
        });
    }

    private void initializeSelectedBuilding(){
        cityOrganizer.selectedBuildingProperty().addListener((observable, oldBuilding, newBuilding) -> {
            refreshBuildingText();
        });
    }

    private void refreshBuildingText(){
        Building hoveredBuilding = cityOrganizer.hoverBuildingProperty().getValue();
        Building selectedBuilding = cityOrganizer.selectedBuildingProperty().getValue();

        if(hoveredBuilding != null){
            setBuildingText(hoveredBuilding);
        }else if(selectedBuilding != null){
            setBuildingText(selectedBuilding);
        }else{
            clearBuildingText();
        }
    }

    private void setBuildingText(Building building){
        buildingName.setText(building.getName());
        buildingSize.setText(building.getSizeMetric()+"");
        buildingHeight.setText(building.getHeightMetric()+"");
        buildingColor.setText(building.getColorMetric()+"");
    }

    private void clearBuildingText(){
        buildingName.setText("");
        buildingSize.setText("");
        buildingHeight.setText("");
        buildingColor.setText("");
    }
}
