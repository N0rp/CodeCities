package core;

import city.CityPackage;
import cityview.CityBundle;
import cityview.CityView;
import city.MockCityPackageFactory;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import parse.csv.CsvParseException;
import parse.sourcemeter.SourceMeterPackageReader;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Richard on 6/7/2015.
 */
public class CodeCityViewer extends Application {

    private static final double CONTROL_MULTIPLIER = 0.1;
    private static final double SHIFT_MULTIPLIER = 10.0;
    private static final double MOUSE_SPEED = 0.1;
    private static final double ROTATION_SPEED = 2.0;
    private static final double TRACK_SPEED = 0.3;

    private double mousePosX;
    private double mousePosY;
    private double mouseOldX;
    private double mouseOldY;
    private double mouseDeltaX;

    private final static int modifierFactor = 1;

    private double mouseDeltaY;
    private static final double CAMERA_INITIAL_DISTANCE = -1000;
    private static final double CAMERA_INITIAL_X_ANGLE = 20.0;
    private static final double CAMERA_INITIAL_Y_ANGLE = 0;
    private static final double CAMERA_NEAR_CLIP = 0.1;
    private static final double CAMERA_FAR_CLIP = 10000.0;

    private final PerspectiveCamera camera = new PerspectiveCamera(true);
    private final Xform world = new Xform();
    private final Xform cameraXform = new Xform();
    private final Xform cameraXform2 = new Xform();
    private final Xform cameraXform3 = new Xform();

    private void buildCamera(Group root) {
        root.getChildren().add(cameraXform);
        cameraXform.getChildren().add(cameraXform2);
        cameraXform2.getChildren().add(cameraXform3);
        cameraXform3.getChildren().add(camera);
        cameraXform3.setRotateZ(180.0);

        camera.setNearClip(CAMERA_NEAR_CLIP);
        camera.setFarClip(CAMERA_FAR_CLIP);
        camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
        cameraXform.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
        cameraXform.rx.setAngle(CAMERA_INITIAL_X_ANGLE);
    }

    public void buildWorld() throws Exception {
        // Create and position camera
        Group root = new Group();
        buildCamera(root);

        // Build the Scene Graph
        //CityView city = new CityView(Arrays.asList(MockCityPackageFactory.getMediumSizePackage()));
        CityView city = new CityView(getSourceMeterRootPackages());
        root.getChildren().addAll(city);

        // Use a SubScene
        SubScene subScene = new SubScene(root, 300, 300, true, SceneAntialiasing.BALANCED);
        handleMouse(subScene, world);
        subScene.setFill(Color.ALICEBLUE);
        subScene.setCamera(camera);

        Group group = new Group();
        group.getChildren().add(subScene);
        world.getChildren().addAll(subScene);
    }

    public static final String PATH_SOURCE_METER_METHOD = "src/main/resources/log4j-1.2.17-Method.csv";
    public static final String PATH_SOURCE_METER_CLASS = "src/main/resources/log4j-1.2.17-Class.csv";
    public static final String PATH_SOURCE_METER_PACKAGE = "src/main/resources/log4j-1.2.17-Package.csv";

    private List<CityPackage> getSourceMeterRootPackages() throws CsvParseException {
        SourceMeterPackageReader sourceMeterPackageReader = new SourceMeterPackageReader(
                PATH_SOURCE_METER_PACKAGE,
                PATH_SOURCE_METER_CLASS,
                PATH_SOURCE_METER_METHOD);
        List<CityPackage> cityPackages = sourceMeterPackageReader.createCityPackages();

        return cityPackages;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setResizable(false);
        buildWorld();
        Scene scene = new Scene(world);
        primaryStage.setScene(scene);

        handleKeyboard(scene, world);
        primaryStage.show();
    }

    private void handleKeyboard(Scene scene, final Node root) {

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case Z:
                    cameraXform2.t.setX(0.0);
                    cameraXform2.t.setY(0.0);
                    cameraXform.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
                    cameraXform.rx.setAngle(CAMERA_INITIAL_X_ANGLE);
                    break;
                case UP:
                case W:
                    cameraXform2.t.setZ(cameraXform2.t.getZ() + 10);
                    break;
                case DOWN:
                case S:
                    cameraXform2.t.setZ(cameraXform2.t.getZ() - 10);
                    break;
                case LEFT:
                case A:
                    cameraXform2.t.setX(cameraXform2.t.getX() + 10);
                    break;
                case RIGHT:
                case D:
                    cameraXform2.t.setX(cameraXform2.t.getX() - 10);
                    break;
            }
        });
    }



    private void handleMouse(SubScene scene, final Node root) {
        scene.setOnMousePressed(me -> {
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            mouseOldX = me.getSceneX();
            mouseOldY = me.getSceneY();
        });
        scene.setOnMouseDragged(me -> moveCamera(me));
        scene.setOnScroll(se -> {
            final double scalingFactor = 10;
            double cameraZ = se.getDeltaY();
            camera.setTranslateZ( camera.getTranslateZ() + (scalingFactor * cameraZ));
        });
    }

    private void moveCamera(MouseEvent me){
        mouseOldX = mousePosX;
        mouseOldY = mousePosY;
        mousePosX = me.getSceneX();
        mousePosY = me.getSceneY();
        mouseDeltaX = (mousePosX - mouseOldX);
        mouseDeltaY = (mousePosY - mouseOldY);

        double modifier = 1.0;

        if (me.isControlDown()) {
            modifier = CONTROL_MULTIPLIER;
        }
        if (me.isShiftDown()) {
            modifier = SHIFT_MULTIPLIER;
        }
        if (me.isPrimaryButtonDown()) {
            cameraXform.ry.setAngle(cameraXform.ry.getAngle() -
                    mouseDeltaX * modifierFactor * modifier * ROTATION_SPEED);  //
            cameraXform.rx.setAngle(cameraXform.rx.getAngle() +
                    mouseDeltaY * modifierFactor * modifier * ROTATION_SPEED);  // -
        } else if (me.isSecondaryButtonDown()) {
            double z = camera.getTranslateZ();
            double newZ = z + mouseDeltaX * MOUSE_SPEED * modifier;
            camera.setTranslateZ(newZ);
        } else if (me.isMiddleButtonDown()) {
            cameraXform2.t.setX(cameraXform2.t.getX() +
                    mouseDeltaX * MOUSE_SPEED * modifier * TRACK_SPEED);  // -
            cameraXform2.t.setY(cameraXform2.t.getY() +
                    mouseDeltaY * MOUSE_SPEED * modifier * TRACK_SPEED);  // -
        }
    }

    /**
     * Java main for when running without JavaFX launcher
     */
    public static void main(String[] args) {
        launch(args);
    }

}
