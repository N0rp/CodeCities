package parse.csv;

import parse.csv.HeaderEnum;

/**
 * Created by Richard on 7/21/2015.
 */
public class MappingTuple {
    public HeaderEnum headerEnum;
    public int index;
    public String name;

    public MappingTuple(HeaderEnum headerEnum, int index, String name){
        this.headerEnum = headerEnum;
        this.index = index;
        this.name = name;
    }
}
