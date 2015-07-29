package core;

import cityview.widget.CityExplorer;
import cityview.widget.CityOrganizer;
import cityview.widget.CityOverlay;
import graph.Node;
import javafx.scene.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Created by Richard on 7/29/2015.
 */
public class CodeCitySceneBuilder {

    private final PerspectiveCamera camera = new PerspectiveCamera(true);
    private final CameraAndInteractionManager cameraAndInteractionManager = new CameraAndInteractionManager(camera);

    private final Xform world = new Xform();
    private SubScene cityScene;
    private CityOverlay overlay;
    private CityExplorer explorer;

    private CodeCitySceneBuilder(){

    }

    public static Scene buildScene(Node node) throws Exception {
        return new CodeCitySceneBuilder().createScene(node);
    }

    private Scene createScene(Node node) throws Exception {
        createContent(node);
        Parent all = createArrangedContent();

        world.getChildren().addAll(all);
        Scene scene = new Scene(world);
        cameraAndInteractionManager.setKeyboardHandler(scene, world);

        return scene;
    }

    private void createContent(Node node) throws Exception{
        CityOrganizer cityOrganizer = new CityOrganizer(node);

        cityScene = createCityScene(cityOrganizer, cameraAndInteractionManager);
        cameraAndInteractionManager.setMouseHandler(cityScene);
        overlay = new CityOverlay(cityOrganizer);
        explorer = new CityExplorer(cityOrganizer);
    }

    private Parent createArrangedContent(){
        VBox all = new VBox();
        HBox content = new HBox();

        all.getChildren().addAll(overlay, content);
        content.getChildren().addAll(explorer, cityScene);

        return all;
    }

    private SubScene createCityScene(CityOrganizer cityOrganizer, CameraAndInteractionManager cameraAndInteractionManager) throws Exception {
        // Create and position camera
        Group root = new Group();
        cameraAndInteractionManager.buildCamera(root);

        // Build the Scene Graph
        root.getChildren().addAll(cityOrganizer);

        // Use a SubScene
        SubScene subScene = new SubScene(root, 300, 300, true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.ALICEBLUE);
        subScene.setCamera(cameraAndInteractionManager.getCamera());

        Group group = new Group();
        group.getChildren().add(subScene);

        return subScene;
    }




}
