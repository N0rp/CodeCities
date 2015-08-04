package cityview.structure;

import graph.Leaf;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableDoubleValue;
import javafx.beans.value.ObservableStringValue;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.text.Text;

/**
 * Created by Richard on 7/6/2015.
 */
public class Building extends Group implements Structure {

    private StringProperty sizeMetricNameProperty = new SimpleStringProperty();
    private StringProperty heightMetricNameProperty = new SimpleStringProperty();
    private StringProperty colorMetricNameProperty = new SimpleStringProperty();

    private BooleanProperty isHover = new SimpleBooleanProperty();
    private BooleanProperty isSelected = new SimpleBooleanProperty();

    private double maxColorMetric;

    private Box buildingBox;
    private Text buildingLabel;

    private Leaf model;

    public Building(Leaf model){
        initChildren();
        setData(model);
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
        setOnMouseClicked(me -> {
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

    private void setData(Leaf model){
        this.model = model;
        this.buildingLabel.setText(model.getName());
    }

    @Override
    public String getName(){
        return model.getName();
    }

    @Override
    public double getStructureWidth() {
        return structureWidthProperty().doubleValue();
    }

    @Override
    public ObservableDoubleValue structureWidthProperty() {
        return buildingBox.widthProperty();
    }

    @Override
    public double getStructureDepth() {
        return structureDepthProperty().doubleValue();
    }

    @Override
    public ObservableDoubleValue structureDepthProperty() {
        return buildingBox.depthProperty();
    }


    public double getMetric(String metricName){
        double metric = model.getMetric(metricName);
        if(metric <= 0){
            metric = 1;
        }
        return metric;
    }
    public double getHeightMetric(){ return model.getMetric(getHeightMetricName()); }

    public double getColorMetric(){
        return model.getMetric(getColorMetricName());
    }

    public void normalizeHeightMetric(double maxHeightMetric, double maxBuildingHeight){
        double normalizedHeight = getHeightMetric() / maxHeightMetric * maxBuildingHeight;
        setHeight(normalizedHeight);
    }
    public void normalizeColorMetric(double maxColorMetric){
        this.maxColorMetric = maxColorMetric;
        this.buildingBox.setMaterial(new PhongMaterial(getBuildingColor(model.getMetric(getColorMetricName()), maxColorMetric)));
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

    @Override
    public String toString(){
        String result = "Building ["+getName();

        result += " (x="+(int)getTranslateX();
        result +=";y="+(int)getTranslateZ();
        result +=";z="+(int)getTranslateZ()+")";
        result += " (Width="+(int)getWidth();
        result += ";Height="+(int)getHeight();
        result += ";Depth="+(int)getDepth()+")";
        result += ", Size="+getSizeMetricName()+":"+(int)getSizeMetric();
        result += ", Height="+getHeightMetricName()+":"+(int)getHeightMetric();
        result += ", Color="+getColorMetricName()+":"+(int)getColorMetric();

        result += "]";
        return result;
    }


    public Leaf getModel() {
        return model;
    }


    public double getWidth(){
        return buildingBox.getWidth();
    }
    private void setWidth(double width){
        buildingBox.setWidth(width);
    }


    public double getHeight(){
        return buildingBox.getHeight();
    }
    private void setHeight(double height){
        buildingBox.setHeight(height);
        buildingBox.setTranslateY(height / 2);
    }


    public double getDepth(){
        return buildingBox.getDepth();
    }
    private void setDepth(double depth){
        buildingBox.setDepth(depth);
    }


    public ObservableBooleanValue isHoverProperty() {
        return isHover;
    }
    public Boolean getIsHover() {
        return isHover.get();
    }


    public ObservableBooleanValue isSelectedProperty() {
        return isSelected;
    }
    public boolean isSelected(){
        return isSelectedProperty().get();
    }
    public void setSelected(boolean isSelected){
        this.isSelected.setValue(isSelected);
    }


    public ObservableStringValue getSizeMetricNameProperty(){
        return sizeMetricNameProperty;
    }
    public String getSizeMetricName(){
        return sizeMetricNameProperty.getValue();
    }
    public void setSizeMetricName(String sizeMetricName){
        sizeMetricNameProperty.setValue(sizeMetricName);
        double size = getMetric(sizeMetricName);
        double width = Math.sqrt(size);
        setDepth(width);
        setWidth(width);
    }
    public double getSizeMetric(){
        return model.getMetric(getSizeMetricName());
    }


    public ObservableStringValue getHeightMetricNameProperty(){
        return heightMetricNameProperty;
    }
    public String getHeightMetricName(){
        return heightMetricNameProperty.getValue();
    }
    public void setHeightMetricName(String heightMetricName){
        heightMetricNameProperty.setValue(heightMetricName);
        double height = getMetric(heightMetricName);
        setHeight(height);
    }


    public ObservableStringValue getColorMetricNameProperty(){
        return colorMetricNameProperty;
    }
    public String getColorMetricName(){
        return colorMetricNameProperty.getValue();
    }
    public void setColorMetricName(String colorMetricName){
        colorMetricNameProperty.setValue(colorMetricName);
    }

}
