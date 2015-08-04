package facade;

import cityview.structure.Structure;

/**
 * Created by Richard on 8/4/2015.
 */
public class SourceMeterDataFactory {

    String pathForPackage;
    String pathForClass;
    String pathForMethod;

    private SourceMeterDataFactory(){
    }

    public static SourceMeterDataFactory buildWithPackage(String packagePath){
        SourceMeterDataFactory factory = new SourceMeterDataFactory();
        factory.pathForPackage = packagePath;
        return factory;
    }

    public SourceMeterDataFactory addClass(String pathForClass){
        this.pathForClass = pathForClass;
        return this;
    }

    public SourceMeterDataFactory addMethod(String pathForMethod){
        this.pathForMethod = pathForMethod;
        return this;
    }

    public SourceMeterData create(){
        return new SourceMeterData(this);
    }

}
