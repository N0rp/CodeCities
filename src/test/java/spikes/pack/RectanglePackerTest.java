package spikes.pack;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.shape.Shape;
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


        RectanglePacker rectanglePacker = new RectanglePacker(Arrays.asList(rectangles));
        List<RectangleRow> packedRectangles = rectanglePacker.pack();

        Assert.assertEquals(1, packedRectangles.size());

        assertIsSquare(packedRectangles);
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

        RectanglePacker rectanglePacker = new RectanglePacker(Arrays.asList(rectangles));
        List<RectangleRow> packedRectangles = rectanglePacker.pack();

        Assert.assertEquals(2, packedRectangles.size());

        assertIsSquare(packedRectangles);
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


        RectanglePacker rectanglePacker = new RectanglePacker(Arrays.asList(rectangles));
        List<RectangleRow> packedRectangles = rectanglePacker.pack();

        Assert.assertEquals(2, packedRectangles.size());

        assertIsSquare(packedRectangles);
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


        RectanglePacker rectanglePacker = new RectanglePacker(Arrays.asList(rectangles));
        List<RectangleRow> packedRectangles = rectanglePacker.pack();

        Assert.assertEquals(3, packedRectangles.size());

        assertIsSquare(packedRectangles);
    }

    private void assertIsSquare(List<RectangleRow> packedRectangles){
        Rectangle bounds = getPackedBounds(packedRectangles);
        Assert.assertEquals((int)bounds.getWidth(), (int)bounds.getHeight());
    }

     private Rectangle getPackedBounds(List<RectangleRow> packedRectangles){
         Rectangle bounds = new Rectangle();
         for(RectangleRow row : packedRectangles){
             for(Rectangle rectangle : row.getRectangles()){

                 if(rectangle.getX() + rectangle.getWidth() > bounds.getWidth()){
                     bounds.setWidth(rectangle.getX() + rectangle.getWidth());
                 }
                 if(rectangle.getY() + rectangle.getHeight() > bounds.getHeight()){
                     bounds.setHeight(rectangle.getY() + rectangle.getHeight());
                 }
             }
         }

         return bounds;
     }

}
