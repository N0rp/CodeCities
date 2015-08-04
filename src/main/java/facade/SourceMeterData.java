package facade;

import graph.Node;
import parse.csv.CsvParseException;
import parse.sourcemeter.SourceMeterPackageReader;

/**
 * Created by Richard on 8/4/2015.
 */
public class SourceMeterData {

    private final String pathForPackage;
    private final String pathForClass;
    private final String pathForMethod;

    SourceMeterData(SourceMeterDataFactory factory){
        this.pathForPackage = factory.pathForPackage;
        this.pathForClass = factory.pathForClass;
        this.pathForMethod = factory.pathForMethod;
    }

    public String getPathForPackage() {
        return pathForPackage;
    }

    public String getPathForClass() {
        return pathForClass;
    }

    public String getPathForMethod() {
        return pathForMethod;
    }

}
