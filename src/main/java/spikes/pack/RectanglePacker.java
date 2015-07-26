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

    private List<Rectangle> rectangles;

    public RectanglePacker(List<Rectangle> rectangles){
        this.rectangles = rectangles;
    }

    public List<RectangleRow> pack(){
        List<Rectangle> remainingRectangles = new LinkedList<>(rectangles);
        List<RectangleRow> packedRectangles = new LinkedList<>();

        double size = getPackedSize();
        double maxWidth = Math.sqrt(size);

        double currentHeight = 0;
        for(ListIterator<Rectangle> iterator = remainingRectangles.listIterator(); iterator.hasNext();){
            Rectangle rectangle = iterator.next();
            iterator.remove();

            List<Rectangle> rectangleRow = removeSimilarShapeRectangles(iterator, maxWidth - rectangle.getWidth());
            rectangleRow.add(0, rectangle);
            RectangleRow row = new RectangleRow(rectangleRow, currentHeight, maxWidth);
            packedRectangles.add(row);
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

    public double getPackedSize(){
        double size = rectangles.stream().mapToDouble(rectangle -> rectangle.getHeight() * rectangle.getWidth()).sum();
        return size;
    }

}
