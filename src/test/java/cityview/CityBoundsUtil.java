package cityview;

import cityview.structure.Block;
import cityview.structure.BoundsCalculator;
import cityview.structure.Building;
import javafx.scene.shape.Rectangle;
import org.junit.Assert;

import java.util.List;

/**
 * Created by Richard on 7/28/2015.
 */
public class CityBoundsUtil {


    public static void assertIsRectangle(Block block, int width, int height){
        Rectangle bounds = BoundsCalculator.getBlockBounds(block);
        Assert.assertEquals(width, (int)bounds.getWidth());
        Assert.assertEquals(height, (int)bounds.getHeight());
    }


    public static void assertIsSquare(Block block){
        Rectangle bounds = BoundsCalculator.getBlockBounds(block);
        Assert.assertEquals((int) bounds.getWidth(), (int) bounds.getHeight());
    }


}
