package core;

import cityview.City;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBuilder;

import java.io.IOException;

/**
 * Created by Richard on 7/28/2015.
 */
public class CityOverlay extends VBox {

    private City city;

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

    public CityOverlay(City city){
        this.city = city;

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
    }

    private void initializeSizeChoiceBox(){
        sizeMetric.setItems(FXCollections.observableArrayList(city.getMetricNames()));
        sizeMetric.getSelectionModel().selectedItemProperty().addListener((observable, oldItem, newItem) -> {
            if(newItem != null && newItem.length() > 0){
                city.setSizeMetricName(newItem);
            }
        });
        sizeMetric.getSelectionModel().selectFirst();
    }

    private void initializeHeightChoiceBox(){
        heightMetric.setItems(FXCollections.observableArrayList(city.getMetricNames()));
        heightMetric.getSelectionModel().selectedItemProperty().addListener((observable, oldItem, newItem) -> {
            if(newItem != null && newItem.length() > 0){
                city.setHeightMetricName(newItem);
            }
        });
        heightMetric.getSelectionModel().selectFirst();
    }

    private void initializeColorChoiceBox(){
        colorMetric.setItems(FXCollections.observableArrayList(city.getMetricNames()));
        colorMetric.getSelectionModel().selectedItemProperty().addListener((observable, oldItem, newItem) -> {
            if(newItem != null && newItem.length() > 0){
                city.setColorMetricName(newItem);
            }
        });
        colorMetric.getSelectionModel().selectFirst();
    }

    private void initializeHoveredBuilding(){
        city.selectedBuildingProperty().addListener((observable, oldBuilding, newBuilding) -> {
            clearCurrentBuildingText();
            if(newBuilding != null){
                buildingName.setText(newBuilding.getName());
                buildingSize.setText(newBuilding.getSizeMetric()+"");
                buildingHeight.setText(newBuilding.getHeightMetric()+"");
                buildingColor.setText(newBuilding.getColorMetric()+"");
            }
        });
    }

    private void clearCurrentBuildingText(){
        buildingName.setText("");
        buildingSize.setText("");
        buildingHeight.setText("");
        buildingColor.setText("");
    }
}
