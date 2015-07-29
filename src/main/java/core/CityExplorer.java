package core;

import cityview.Block;
import cityview.Building;
import cityview.City;
import cityview.Structure;
import graph.Leaf;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;

/**
 * Created by Richard on 7/29/2015.
 */
public class CityExplorer extends VBox{

    @FXML
    private ListView<Building> cityContent;

    private City city;

    public CityExplorer(City city){
        this.city = city;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/core/CityExplorer.fxml"));
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
        initializeExplorer();
    }

    private void initializeExplorer(){
        List<Building> allBuildings = city.getRootBlock().findAllBuildings();
        ObservableList<Building> observableBuildings = FXCollections.observableArrayList(allBuildings);
        cityContent.setItems(observableBuildings);
        cityContent.setCellFactory(list -> new XCell() );
        cityContent.getSelectionModel().selectedItemProperty().addListener((observable, oldBuilding, newBuilding) -> {
            if(newBuilding != null){
                newBuilding.setSelected(true);
            }else if(oldBuilding != null){
                oldBuilding.setSelected(false);
            }
        });
    }

    static class XCell extends ListCell<Building> {
        private HBox hbox = new HBox();
        private Label label = new Label("(empty)");

        public XCell() {
            super();
            hbox.getChildren().addAll(label);

        }

        @Override
        public void updateItem(Building item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            }else{
                String metric = "[LoC="+item.getMetric(Leaf.LINES_OF_CODE)+"]";
                String coordinates = "["+(int)item.getTranslateX()+", "+(int)item.getTranslateY()+", "+(int)item.getTranslateZ()+"]";
                String size = "["+(int)item.getWidth()+", "+(int)item.getDepth()+"]";
                label.setText(item.getName()+" "+metric+coordinates+size);
                setGraphic(hbox);

                label.backgroundProperty().bind(Bindings.when(item.isHoverProperty())
                        .then(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY)))
                        .otherwise(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY))));

                item.isSelectedProperty().addListener((observable, oldValue, newValue) -> {
                    updateSelected(newValue);
                });
            }
        }
    }

}
