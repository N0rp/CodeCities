package spikes.pack;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Richard on 7/22/2015.
 */
public class RectangleApp extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("java-buddy.blogspot.com");
        Group root = new Group();
        Scene scene = new Scene(root, 400, 300, Color.WHITE);

        Rectangle rectA = RectangleBuilder.create()
                .x(50)
                .y(50)
                .width(100)
                .height(100)
                .build();

        Rectangle rectB = RectangleBuilder.create()
                .x(100)
                .y(100)
                .width(100)
                .height(100)
                .fill(Color.BLUE)
                .build();

        Rectangle rectC = RectangleBuilder.create()
                .x(250)
                .y(50)
                .width(100)
                .height(100)
                .fill(Color.RED)
                .build();

        Rectangle[] rectangles = new Rectangle[]{rectA, rectB, rectC};


        RectanglePacker rectanglePacker = new RectanglePacker();
        List<Rectangle> packedRectangles = rectanglePacker.pack(Arrays.asList(rectangles), 200);

        root.getChildren().addAll(packedRectangles);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
