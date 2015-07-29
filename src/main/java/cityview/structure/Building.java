package cityview.structure;

import graph.Leaf;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.text.Text;

/**
 * Created by Richard on 7/6/2015.
 */
public class Building extends Group implements Structure {

    private double maxColorMetric;

    private String sizeMetricName;
    private String heightMetricName;
    private String colorMetricName;

    public Leaf getLeaf() {
        return leaf;
    }

    private Box buildingBox;
    private Text buildingLabel;

    private Leaf leaf;

    public Boolean getIsHover() {
        return isHover.get();
    }

    public ObservableBooleanValue isHoverProperty() {
        return isHover;
    }

    public boolean isSelected(){
        return isSelectedProperty().get();
    }

    public void setSelected(boolean isSelected){
        this.isSelected.setValue(isSelected);
    }

    public ObservableBooleanValue isSelectedProperty() {
        return isSelected;
    }

    private BooleanProperty isHover = new SimpleBooleanProperty();
    private BooleanProperty isSelected = new SimpleBooleanProperty();

    public Building(Leaf leaf){
        initChildren();
        setData(leaf);
        setSizeMetricName(Leaf.LINES_OF_CODE);
        setHeightMetricName(Leaf.LINES_OF_CODE);
        setColorMetricName(Leaf.LINES_OF_CODE);
    }

    private void initChildren(){
        this.buildingBox = new Box();
        this.buildingLabel = new Text("test");

        setOnMouseEntered(me -> {
            isHover.set(true);
        });
        setOnMouseExited(me -> {
            isHover.set(false);
        });
        setOnMouseClicked(me ->{
            setSelected(true);
        });

        isHover.addListener((observable) -> {
            refreshColor();
        });

        isSelected.addListener((observable) -> {
            refreshColor();
        });

        getChildren().addAll(buildingBox, buildingLabel);
    }

    private void refreshColor(){
        boolean isHover = isHover();
        boolean isSelected = isSelected();
        if(isHover){
            this.buildingBox.setMaterial(new PhongMaterial(Color.AQUA));
        }else if(isSelected){
            this.buildingBox.setMaterial(new PhongMaterial(Color.BLUE));
        }else{
            normalizeColorMetric(maxColorMetric);
        }
    }

    private void setData(Leaf leaf){
        this.leaf = leaf;
        this.buildingLabel.setText(leaf.getName());
    }

    public String getName(){
        return leaf.getName();
    }

    public void setSizeMetricName(String sizeMetricName){
        this.sizeMetricName = sizeMetricName;
        double size = getMetric(sizeMetricName);
        double width = Math.sqrt(size);
        setDepth(width);
        setWidth(width);
    }

    public void setHeightMetricName(String heightMetricName){
        this.heightMetricName = heightMetricName;
        double height = getMetric(heightMetricName);
        setHeight(height);
    }

    public void setColorMetricName(String colorMetricName){
        this.colorMetricName = colorMetricName;
    }

    public double getSizeMetric(){
        return leaf.getMetric(sizeMetricName);
    }

    public double getHeightMetric(){ return leaf.getMetric(heightMetricName); }

    public double getColorMetric(){
        return leaf.getMetric(colorMetricName);
    }

    public void normalizeHeightMetric(double maxHeightMetric, double maxBuildingHeight){
        double normalizedHeight = getHeightMetric() / maxHeightMetric * maxBuildingHeight;
        setHeight(normalizedHeight);
    }


    public void normalizeColorMetric(double maxColorMetric){
        this.maxColorMetric = maxColorMetric;
        this.buildingBox.setMaterial(new PhongMaterial(getBuildingColor(leaf.getMetric(colorMetricName), maxColorMetric)));
    }

    @Override
    public String toString(){
        String result = "Building [";

        result += "Width="+(int)getWidth();
        result += ", Depth="+(int)getDepth();
        result += ", SizeMetric="+sizeMetricName+":"+(int)getSizeMetric();

        result += "]";
        return result;
    }

    public double getMetric(String metricName){
        return leaf.getMetric(metricName);
    }

    public double getWidth(){
        return buildingBox.getWidth();
    }

    private void setWidth(double width){
        buildingBox.setWidth(width);
    }

    private void setDepth(double depth){
        buildingBox.setDepth(depth);
    }

    public double getDepth(){
        return buildingBox.getDepth();
    }

    private void setHeight(double height){
        buildingBox.setHeight(height);
        buildingBox.setTranslateY(height/2);
    }



    public void resizeToDepth(double newDepth){
        double size = getDepth() * getWidth();
        double newWidth = size / newDepth;

        setWidth(newWidth);
        setDepth(newDepth);
    }

    public void resizeToWidth(double newWidth){
        double size = getDepth() * getWidth();
        double newHeight = size / newWidth;

        setWidth(newWidth);
        setDepth(newHeight);
    }

    private Color getBuildingColor(double value, double max){
        double middleStart = max / 3;
        double middleEnd = middleStart * 2;
        if(value < middleStart){
            return Color.GREEN;
        }else if(value < middleEnd){
            return Color.ORANGE;
        }else{
            return Color.RED;
        }
    }

}