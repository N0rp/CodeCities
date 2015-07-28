package graph;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Richard on 7/5/2015.
 */
public class Leaf {

    public final static String LINES_OF_CODE = "LinesOfCode";
    public final static String MC_CABE = "McCabe";
    public final static String NESTING_LEVEL = "NestingLevel";

    private Map<String, Double> nameToMetric;

    public String getName() {
        return name;
    }

    public double getMetric(String metricName){
        return nameToMetric.get(metricName);
    }

    private String name;

    public Leaf(String name, Map<String, Double> nameToMetric){
        this.name = name;
        this.nameToMetric = nameToMetric;
    }

    public Set<String> getAllMetricNames(){
        return nameToMetric.keySet();
    }

}
