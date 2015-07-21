package parse.sourcemeter;

import parse.csv.HeaderEnum;
import parse.csv.MappingTuple;

/**
 * Created by Richard on 7/21/2015.
 */
public class SourceMeterMappings {

    public final static MappingTuple[] methodMappings = new MappingTuple[]{
            new MappingTuple(HeaderEnum.ID, 0, "\"ID\""),
            new MappingTuple(HeaderEnum.NAME, 1, "\"Name\""),
            new MappingTuple(HeaderEnum.PARENT, 3, "\"Parent\""),
            new MappingTuple(HeaderEnum.PATH, 5, "\"Path\""),
            new MappingTuple(HeaderEnum.MCC_COMPLEXITY, 18, "\"McCC\""),
            new MappingTuple(HeaderEnum.NESTING_LEVEL, 19, "\"NL\""),
            new MappingTuple(HeaderEnum.LINES_OF_CODE, 29, "\"LOC\""),
    };

    public final static MappingTuple[] classMappings = new MappingTuple[]{
            new MappingTuple(HeaderEnum.ID, 0, "\"ID\""),
            new MappingTuple(HeaderEnum.NAME, 1, "\"Name\""),
            new MappingTuple(HeaderEnum.PARENT, 3, "\"Parent\""),
            new MappingTuple(HeaderEnum.PATH, 5, "\"Path\""),
            new MappingTuple(HeaderEnum.NESTING_LEVEL, 19, "\"NL\""),
            new MappingTuple(HeaderEnum.LINES_OF_CODE, 41, "\"LOC\""),
    };
}
