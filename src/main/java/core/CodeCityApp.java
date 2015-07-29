package core;

import cityview.widget.CityExplorer;
import cityview.widget.CityOrganizer;
import cityview.widget.CityOverlay;
import graph.MockNodeFactory;
import graph.Node;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import parse.csv.CsvParseException;
import parse.sourcemeter.SourceMeterPackageReader;

/**
 * Created by Richard on 6/7/2015.
 */
public class CodeCityApp extends Application {

    public static final String PATH_SOURCE_METER_METHOD = "src/main/resources/log4j-1.2.17-Method.csv";
    public static final String PATH_SOURCE_METER_CLASS = "src/main/resources/log4j-1.2.17-Class.csv";
    public static final String PATH_SOURCE_METER_PACKAGE = "src/main/resources/log4j-1.2.17-Package.csv";

    private Node getSourceMeterRootPackages() throws CsvParseException {
        SourceMeterPackageReader sourceMeterPackageReader = new SourceMeterPackageReader(
                PATH_SOURCE_METER_PACKAGE,
                PATH_SOURCE_METER_CLASS,
                PATH_SOURCE_METER_METHOD);
        Node nodes = sourceMeterPackageReader.createCityPackages();

        return nodes;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = CodeCitySceneBuilder.buildScene(MockNodeFactory.getSimplePackage());
        //Scene scene = CodeCitySceneBuilder.buildScene(getSourceMeterRootPackages());

        primaryStage.setResizable(false);
        primaryStage.setScene(scene);

        primaryStage.show();
    }



    /**
     * Java main for when running without JavaFX launcher
     */
    public static void main(String[] args) {
        launch(args);
    }

}
