package parse.sourcemeter;

import graph.Leaf;
import graph.Node;
import parse.csv.CsvParseException;
import parse.csv.CsvParser;
import parse.csv.HeaderEnum;

import java.util.*;

/**
 * Created by Richard on 7/21/2015.
 */
public class SourceMeterPackageReader {

    private String packagePath;
    private String classPath;
    private String methodPath;

    public SourceMeterPackageReader(String packagePath, String classPath, String methodPath){
        this.packagePath = packagePath;
        this.classPath = classPath;
        this.methodPath = methodPath;
    }

    public List<Node> createCityPackages() throws CsvParseException {
        CsvParser csvParser = new CsvParser();
        List<Map<HeaderEnum, String>> packageMappings = csvParser.parse(packagePath, SourceMeterMappings.packageMappings);
        List<Map<HeaderEnum, String>> classMappings = csvParser.parse(classPath, SourceMeterMappings.classMappings);
        List<Map<HeaderEnum, String>> methodMappings = csvParser.parse(methodPath, SourceMeterMappings.methodMappings);

        List<Node> nodes = createCityPackages(packageMappings, classMappings, methodMappings);

        return nodes;
    }

    private List<Node> createCityPackages(List<Map<HeaderEnum, String>> packageMappings,
                                                 List<Map<HeaderEnum, String>> classMappings,
                                                 List<Map<HeaderEnum, String>> methodMappings) throws CsvParseException {
        List<Node> nodes = new LinkedList<>();

        for(Map<HeaderEnum, String> packageMapping : packageMappings){
            List<Leaf> leafs = new LinkedList<>();
            String packageId = packageMapping.get(HeaderEnum.ID);
            List<Map<HeaderEnum, String>> matchingClassMappings = removeChildMappings(packageId, classMappings);
            for(Map<HeaderEnum, String> matchingClassMapping : matchingClassMappings){
                String classId = matchingClassMapping.get(HeaderEnum.ID);
                List<Map<HeaderEnum, String>> matchingMethodMappings = removeChildMappings(classId, methodMappings);

                Leaf leaf = createCityBuilding(matchingClassMapping, matchingMethodMappings);
                leafs.add(leaf);
            }
            Node node = createCityPackage(packageMapping, leafs);
            nodes.add(node);
        }

        return nodes;
    }

    private Node createCityPackage(Map<HeaderEnum, String> packageMapping,
                                            List<Leaf> leafs) throws CsvParseException {
        String name = getString(packageMapping, HeaderEnum.NAME);

        Node node = new Node(name, leafs);

        return node;
    }

    private Leaf createCityBuilding(Map<HeaderEnum, String> classMapping,
                                                   List<Map<HeaderEnum, String>> methodMappings) throws CsvParseException {
        Leaf leaf = null;

        String name = getString(classMapping, HeaderEnum.NAME);
        int loc = (int)getDouble(classMapping, HeaderEnum.LINES_OF_CODE);
        int nl = (int)getDouble(classMapping, HeaderEnum.NESTING_LEVEL);

        leaf = new Leaf(name, convertNumbers(classMapping));

        return leaf;
    }

    private Map<String, Double> convertNumbers(Map<HeaderEnum, String> enumToString){
        Map<String, Double> stringToDouble = new HashMap<>();
        for (Map.Entry<HeaderEnum, String> entry : enumToString.entrySet())
        {
            System.out.println(entry.getKey() + "/" + entry.getValue());
            try
            {
                double d = Double.parseDouble(entry.getValue());
                stringToDouble.put(entry.getKey().toString(), d);
            }
            catch(NumberFormatException nfe) { }
        }

        return stringToDouble;
    }

    private void verifyNotNull(Map<HeaderEnum, String> mapping, HeaderEnum headerEnum) throws CsvParseException {
        if(!mapping.containsKey(headerEnum) || mapping.get(headerEnum) == null){
            throw new CsvParseException(mapping.get(HeaderEnum.ROW_NUMBER), headerEnum);
        }
    }

    private double getDouble(Map<HeaderEnum, String> mapping, HeaderEnum headerEnum) throws CsvParseException {
        double result = -1;
        String str = getString(mapping, headerEnum);
        try{
            result = Double.parseDouble(str);
        }catch (NumberFormatException e){
            throw new CsvParseException(mapping.get(HeaderEnum.ROW_NUMBER), headerEnum, str, Double.class);
        }

        return result;
    }

    private String getString(Map<HeaderEnum, String> mapping, HeaderEnum headerEnum) throws CsvParseException {
        verifyNotNull(mapping, headerEnum);
        String str = mapping.get(headerEnum).replace("\"", "");
        return str;
    }

    private List<Map<HeaderEnum, String>> removeChildMappings(String parentId, List<Map<HeaderEnum, String>> childMappings){
        List<Map<HeaderEnum, String>> matchingChildMappings = new LinkedList<>();
        for (Iterator<Map<HeaderEnum, String>> i = childMappings.iterator(); i.hasNext();) {
            Map<HeaderEnum, String> childMapping = i.next();
            if (parentId.equals(childMapping.get(HeaderEnum.PARENT))) {
                matchingChildMappings.add(childMapping);
                i.remove();
            }
        }

        return matchingChildMappings;
    }
}
