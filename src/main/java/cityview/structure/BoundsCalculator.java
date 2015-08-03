package cityview.structure;

import javafx.scene.shape.Rectangle;

import java.util.List;

/**
 * Created by RichardG on 02.08.2015.
 */
public class BoundsCalculator {

    public static Rectangle getBlockBounds(Block block){
        Rectangle bounds = getBuildingBounds(block.getBuildings());
        for(Block subBlock : block.getBlocks()){
            Rectangle blockBounds = getBlockBounds(subBlock);
            bounds = unionBounds(bounds, blockBounds);
        }

        return bounds;
    }

    private static Rectangle getBuildingBounds(List<Building> buildings){
        Rectangle bounds = new Rectangle();
        for(Building building : buildings){
            double translateX = building.getTranslateX();
            double translateZ = building.getTranslateZ();
            double newWidth = translateX + building.getWidth();
            double newHeight = translateZ + building.getDepth();
            bounds = unionBounds(bounds, newWidth, newHeight);
        }

        return bounds;
    }

    private static Rectangle unionBounds(Rectangle bounds, double newWidth, double newHeight){
        if(newWidth > bounds.getWidth()){
            bounds.setWidth(newWidth);
        }
        if(newHeight > bounds.getHeight()){
            bounds.setHeight(newHeight);
        }
        return bounds;
    }

    private static Rectangle unionBounds(Rectangle boundsA, Rectangle boundsB){
        double newWidth = boundsB.getWidth();
        double newHeight = boundsB.getHeight();
        return unionBounds(boundsA, newWidth, newHeight);
    }

}
