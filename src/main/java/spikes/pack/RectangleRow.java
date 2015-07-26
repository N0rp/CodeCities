package spikes.pack;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

import java.util.List;

/**
 * Created by Richard on 7/26/2015.
 */
public class RectangleRow extends Group{

    public List<Rectangle> getRectangles() {
        return rectangles;
    }

    private List<Rectangle> rectangles;

    public RectangleRow(List<Rectangle> rectangleRow, double rowY, double maxWidth){
        this.rectangles = rectangleRow;
        getChildren().addAll(rectangles);
        packRectanglesWithoutSpace(rectangleRow, rowY, maxWidth);
    }

    private void packRectanglesWithoutSpace(List<Rectangle> rectangleRow, double rectangleY, double maxWidth){
        double rectangleX = 0;
        for(Rectangle rectangle : rectangleRow){
            rectangle.setX(rectangleX);
            rectangle.setY(rectangleY);
            rectangleX += rectangle.getWidth();
        }

        double maxHeight = getMaxHeight();
        for(Rectangle rectangle : rectangleRow){
            resizeToHeight(rectangle, maxHeight);
        }

        resizeToContainerWidth(rectangleRow, maxWidth);
    }

    public double getMaxHeight(){
        Rectangle maxHeightRectangle = rectangles.stream().max((rectA, rectB) -> Double.compare(rectA.getHeight(), rectB.getHeight())).get();
        return maxHeightRectangle.getHeight();
    }

    private void resizeToHeight(Rectangle rect, double newHeight){
        double size = rect.getHeight() * rect.getWidth();
        double newWidth = size / newHeight;

        rect.setWidth(newWidth);
        rect.setHeight(newHeight);
    }

    private void resizeToWidth(Rectangle rect, double newWidth){
        double size = rect.getHeight() * rect.getWidth();
        double newHeight = size / newWidth;

        rect.setWidth(newWidth);
        rect.setHeight(newHeight);
    }

    private void resizeToContainerWidth(List<Rectangle> rectangleRow, double maxWidth){
        double currentWidth = 0;
        Rectangle previousRectangle = null;
        for(Rectangle rectangle : rectangleRow){
            if(previousRectangle != null && rectangle.getX() < previousRectangle.getX()){
                System.err.println("The rectangle "+rectangle+" does not come after the previous "+previousRectangle);
            }

            previousRectangle = rectangle;
            currentWidth += rectangle.getWidth();
        }

        double currentX = 0;
        for(Rectangle rectangle : rectangleRow){
            rectangle.setX(currentX);
            double relativeWidth = rectangle.getWidth() / currentWidth;
            double newWidth = relativeWidth * maxWidth;
            resizeToWidth(rectangle, newWidth);
            currentX += newWidth;
        }
    }
}
