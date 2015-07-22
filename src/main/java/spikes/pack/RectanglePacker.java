package spikes.pack;

import javafx.scene.shape.Rectangle;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Richard on 7/22/2015.
 */
public class RectanglePacker {

    public List<Rectangle> pack(List<Rectangle> rectangles, double maxWidth){
        List<Rectangle> remainingRectangles = new LinkedList<>(rectangles);
        List<Rectangle> packedRectangles = new LinkedList<>();

        double currentHeight = 0;
        for(ListIterator<Rectangle> iterator = remainingRectangles.listIterator(); iterator.hasNext();){
            Rectangle rectangle = iterator.next();
            iterator.remove();

            List<Rectangle> rectangleRow = removeSimilarShapeRectangles(iterator, maxWidth - rectangle.getWidth());
            rectangleRow.add(0, rectangle);
            packRectanglesWithoutSpace(rectangleRow, currentHeight);
            packedRectangles.addAll(rectangleRow);
            Rectangle maxHeightRectangle = rectangleRow.stream().max((rectA, rectB) -> Double.compare(rectA.getHeight(), rectB.getHeight())).get();
            currentHeight += maxHeightRectangle.getHeight();
        }

        return packedRectangles;
    }

    private List<Rectangle> removeSimilarShapeRectangles(ListIterator<Rectangle> followingRectangles, double maxWidth){
        List<Rectangle> similarShapeRectangles = new LinkedList<>();
        while(followingRectangles.hasNext()){
            Rectangle nextRectangle = followingRectangles.next();

            if(nextRectangle.getWidth() <= maxWidth){
                maxWidth -= nextRectangle.getWidth();
                similarShapeRectangles.add(nextRectangle);
                followingRectangles.remove();
            }else{
                // reset iterator
                followingRectangles.previous();
                break;
            }
        }

        return similarShapeRectangles;
    }

    private void packRectanglesWithoutSpace(List<Rectangle> rectangleRow, double rectangleY){
        double rectangleX = 0;
        for(Rectangle rectangle : rectangleRow){
            rectangle.setX(rectangleX);
            rectangle.setY(rectangleY);
            rectangleX += rectangle.getWidth();
        }
    }

}
