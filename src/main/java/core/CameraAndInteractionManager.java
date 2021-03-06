package core;

import javafx.scene.*;
import javafx.scene.input.MouseEvent;

/**
 * Created by Richard on 7/29/2015.
 */
public class CameraAndInteractionManager {

    private static final double CAMERA_INITIAL_DISTANCE = -1000;
    private static final double CAMERA_INITIAL_X_ANGLE = 20.0;
    private static final double CAMERA_INITIAL_Y_ANGLE = 0;
    private static final double CAMERA_NEAR_CLIP = 0.1;
    private static final double CAMERA_FAR_CLIP = 10000.0;

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

    private final PerspectiveCamera camera;
    private final Xform world = new Xform();
    private final Xform cameraXform = new Xform();
    private final Xform cameraXform2 = new Xform();
    private final Xform cameraXform3 = new Xform();

    public CameraAndInteractionManager(PerspectiveCamera camera){
        this.camera = camera;
    }

    public PerspectiveCamera getCamera(){
        return camera;
    }

    public void buildCamera(Group root) {
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

    public void setMouseHandler(SubScene scene) {
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



    public void setKeyboardHandler(Scene scene, final javafx.scene.Node root) {

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
}
