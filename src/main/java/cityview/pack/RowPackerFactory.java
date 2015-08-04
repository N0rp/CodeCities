package cityview.pack;

import cityview.structure.Building;

import java.util.List;

/**
 * Created by Richard on 8/4/2015.
 */
public class RowPackerFactory {

    RowStructures<Building> buildingsInRow;
    double rowWidth;
    double rowZ;

    private RowPackerFactory(){

    }

    public static RowPackerFactory Create(){
        return new RowPackerFactory();
    }

    public RowPackerFactory buildingsInRow(RowStructures<Building> buildingsInRow){
        this.buildingsInRow = buildingsInRow;
        return this;
    }

    public RowPackerFactory rowWidth(double rowWidth){
        this.rowWidth = rowWidth;
        return this;
    }

    public RowPackerFactory rowZ(double rowZ){
        this.rowZ = rowZ;
        return this;
    }

    public RowPacker build(){
        return new RowPacker(this);
    }

}
