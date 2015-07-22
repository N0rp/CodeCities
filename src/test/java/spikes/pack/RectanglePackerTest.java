package spikes.pack;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Richard on 7/22/2015.
 */
public class RectanglePackerTest {

    @Test
    public void testPack_OneRectangleInOneRow_Success() {
        /*
            A
         */
        Rectangle rectA = RectangleBuilder.create()
                .x(50)
                .y(50)
                .width(100)
                .height(100)
                .build();

        Rectangle[] rectangles = new Rectangle[]{rectA};


        RectanglePacker rectanglePacker = new RectanglePacker();
        List<Rectangle> packedRectangles = rectanglePacker.pack(Arrays.asList(rectangles), 100);

        Assert.assertEquals(1, packedRectangles.size());
        Assert.assertEquals(0, (int)packedRectangles.get(0).getX());
        Assert.assertEquals(0, (int)packedRectangles.get(0).getY());
    }

    @Test
    public void testPack_TwoRectanglesInOneRow_Success() {
        /*
            A, B
         */
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

        Rectangle[] rectangles = new Rectangle[]{rectA, rectB};

        RectanglePacker rectanglePacker = new RectanglePacker();
        List<Rectangle> packedRectangles = rectanglePacker.pack(Arrays.asList(rectangles), 200);

        Assert.assertEquals(2, packedRectangles.size());
        Assert.assertEquals(0, (int)packedRectangles.get(0).getX());
        Assert.assertEquals(0, (int)packedRectangles.get(0).getY());

        Assert.assertEquals(100, (int)packedRectangles.get(1).getX());
        Assert.assertEquals(0, (int)packedRectangles.get(1).getY());
    }

    @Test
    public void testPack_TwoRectanglesInTwoRows_Success() {
        /*
            A
            B
         */
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

        Rectangle[] rectangles = new Rectangle[]{rectA, rectB};


        RectanglePacker rectanglePacker = new RectanglePacker();
        List<Rectangle> packedRectangles = rectanglePacker.pack(Arrays.asList(rectangles), 100);

        Assert.assertEquals(2, packedRectangles.size());
        Assert.assertEquals(0, (int)packedRectangles.get(0).getX());
        Assert.assertEquals(0, (int)packedRectangles.get(0).getY());

        Assert.assertEquals(0, (int)packedRectangles.get(1).getX());
        Assert.assertEquals(100, (int)packedRectangles.get(1).getY());
    }

    @Test
    public void testPack_ThreeRectanglesInTowRows_Success() {
        /*
            A, B
            C
         */

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
                .x(200)
                .y(200)
                .width(100)
                .height(100)
                .fill(Color.RED)
                .build();

        Rectangle[] rectangles = new Rectangle[]{rectA, rectB, rectC};


        RectanglePacker rectanglePacker = new RectanglePacker();
        List<Rectangle> packedRectangles = rectanglePacker.pack(Arrays.asList(rectangles), 200);

        Assert.assertEquals(3, packedRectangles.size());
        Assert.assertEquals(0, (int)packedRectangles.get(0).getX());
        Assert.assertEquals(0, (int)packedRectangles.get(0).getY());
        Assert.assertEquals(100, (int)packedRectangles.get(1).getX());
        Assert.assertEquals(0, (int)packedRectangles.get(1).getY());
        Assert.assertEquals(0, (int)packedRectangles.get(2).getX());
        Assert.assertEquals(100, (int)packedRectangles.get(2).getY());
    }

}
