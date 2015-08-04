package facade;

import graph.Node;
import parse.csv.CsvParseException;
import parse.sourcemeter.SourceMeterPackageReader;

/**
 * Created by Richard on 8/4/2015.
 */
public class NodeFacade {

    public Node getNodeFromSourceMeter(SourceMeterData data) throws CsvParseException {
        SourceMeterPackageReader sourceMeterPackageReader = new SourceMeterPackageReader(
                data.getPathForPackage(),
                data.getPathForClass(),
                data.getPathForMethod());
        Node node = sourceMeterPackageReader.createCityPackages();

        return node;
    }

}
