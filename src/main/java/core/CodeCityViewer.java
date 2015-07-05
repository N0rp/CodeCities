package core;

import city.CityBuilding;
import city.CityDrawer;
import city.CityPackage;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

/**
 * Created by Richard on 6/7/2015.
 */
public class CodeCityViewer extends Application {

    private static final double CONTROL_MULTIPLIER = 0.1;
    private static final double SHIFT_MULTIPLIER = 10.0;
    private static final double MOUSE_SPEED = 0.1;
    private static final double ROTATION_SPEED = 2.0;
    private static final double TRACK_SPEED = 0.3;

    double mousePosX;
    double mousePosY;
    double mouseOldX;
    double mouseOldY;
    double mouseDeltaX;

    private final static int modifierFactor = 1;

    double mouseDeltaY;
    private static final double CAMERA_INITIAL_DISTANCE = -1000;
    private static final double CAMERA_INITIAL_X_ANGLE = 70.0;
    private static final double CAMERA_INITIAL_Y_ANGLE = 320.0;
    private static final double CAMERA_NEAR_CLIP = 0.1;
    private static final double CAMERA_FAR_CLIP = 10000.0;

    final PerspectiveCamera camera = new PerspectiveCamera(true);
    final Xform world = new Xform();
    final Xform cameraXform = new Xform();
    final Xform cameraXform2 = new Xform();
    final Xform cameraXform3 = new Xform();

    private void buildCamera(Group root) {
        root.getChildren().add(cameraXform);
        cameraXform.getChildren().add(cameraXform2);
        cameraXform2.getChildren().add(cameraXform3);
        cameraXform3.getChildren().add(camera);
        //cameraXform3.setRotateZ(180.0);

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
        CityBuilding building = new CityBuilding("foo", 700, 100, 0);
        CityBuilding building2 = new CityBuilding("bar", 400, 50, 50);
        CityBuilding building3 = new CityBuilding("gaa", 1000, 10, 100);
        CityPackage pack = new CityPackage("Something", building, building2, building3);
        CityDrawer city = new CityDrawer(pack);
        city.drawPackage(root);

        // Use a SubScene
        SubScene subScene = new SubScene(root, 300,300);
        handleMouse(subScene, world);
        subScene.setFill(Color.ALICEBLUE);
        subScene.setCamera(camera);

        Group group = new Group();
        group.getChildren().add(subScene);
        world.getChildren().addAll(subScene);
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

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case Z:
                        cameraXform2.t.setX(0.0);
                        cameraXform2.t.setY(0.0);
                        cameraXform.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
                        cameraXform.rx.setAngle(CAMERA_INITIAL_X_ANGLE);
                        break;
                } // switch
            } // handle()
        });  // setOnKeyPressed
    }  //  handleKeyboard()



    private void handleMouse(SubScene scene, final Node root) {
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent me) {
                mousePosX = me.getSceneX();
                mousePosY = me.getSceneY();
                mouseOldX = me.getSceneX();
                mouseOldY = me.getSceneY();
            }
        });
        scene.setOnMouseDragged(me -> moveCamera(me));
        scene.setOnScroll(se -> {
            double cameraZ = se.getDeltaY();

            System.out.println(cameraZ + "");
            camera.setTranslateZ(cameraZ);
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
