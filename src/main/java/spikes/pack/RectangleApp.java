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

        Rectangle rectD = RectangleBuilder.create()
                .x(250)
                .y(50)
                .width(50)
                .height(50)
                .arcWidth(20)
                .arcHeight(20)
                .fill(Color.PURPLE)
                .build();

        Rectangle rectE = RectangleBuilder.create()
                .x(250)
                .y(50)
                .width(150)
                .height(150)
                .arcWidth(5)
                .arcHeight(5)
                .fill(Color.BROWN)
                .build();

        Rectangle[] rectangles = new Rectangle[]{rectA, rectB, rectC, rectD, rectE};


        RectanglePacker rectanglePacker = new RectanglePacker(Arrays.asList(rectangles));
        List<RectangleRow> packedRectangles = rectanglePacker.pack();

        root.getChildren().addAll(packedRectangles);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
