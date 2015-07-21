package parse.sourcemeter;

import city.CityBuilding;
import city.CityPackage;
import parse.csv.CsvParseException;
import parse.csv.CsvParser;
import parse.csv.HeaderEnum;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    public List<CityPackage> createCityPackages() throws CsvParseException {
        CsvParser csvParser = new CsvParser();
        List<Map<HeaderEnum, String>> packageMappings = csvParser.parse(packagePath, SourceMeterMappings.packageMappings);
        List<Map<HeaderEnum, String>> classMappings = csvParser.parse(classPath, SourceMeterMappings.classMappings);
        List<Map<HeaderEnum, String>> methodMappings = csvParser.parse(methodPath, SourceMeterMappings.methodMappings);

        List<CityPackage> cityPackages = createCityPackages(packageMappings, classMappings, methodMappings);

        return cityPackages;
    }

    private List<CityPackage> createCityPackages(List<Map<HeaderEnum, String>> packageMappings,
                                                 List<Map<HeaderEnum, String>> classMappings,
                                                 List<Map<HeaderEnum, String>> methodMappings) throws CsvParseException {
        List<CityPackage> cityPackages = new LinkedList<>();

        for(Map<HeaderEnum, String> packageMapping : packageMappings){
            List<CityBuilding> cityBuildings = new LinkedList<>();
            String packageId = packageMapping.get(HeaderEnum.ID);
            List<Map<HeaderEnum, String>> matchingClassMappings = removeChildMappings(packageId, classMappings);
            for(Map<HeaderEnum, String> matchingClassMapping : matchingClassMappings){
                String classId = matchingClassMapping.get(HeaderEnum.ID);
                List<Map<HeaderEnum, String>> matchingMethodMappings = removeChildMappings(classId, methodMappings);

                CityBuilding cityBuilding = createCityBuilding(matchingClassMapping, matchingMethodMappings);
                cityBuildings.add(cityBuilding);
            }
            CityPackage cityPackage = createCityPackage(packageMapping, cityBuildings);
            cityPackages.add(cityPackage);
        }

        return cityPackages;
    }

    private CityPackage createCityPackage(Map<HeaderEnum, String> packageMapping,
                                            List<CityBuilding> cityBuildings) throws CsvParseException {
        String name = getString(packageMapping, HeaderEnum.NAME);

        CityPackage cityPackage = new CityPackage(name, cityBuildings);

        return cityPackage;
    }

    private CityBuilding createCityBuilding(Map<HeaderEnum, String> classMapping,
                                                   List<Map<HeaderEnum, String>> methodMappings) throws CsvParseException {
        CityBuilding cityBuilding = null;

        String name = getString(classMapping, HeaderEnum.NAME);
        int loc = (int)getDouble(classMapping, HeaderEnum.LINES_OF_CODE);
        int nl = (int)getDouble(classMapping, HeaderEnum.NESTING_LEVEL);

        cityBuilding = new CityBuilding(name, loc, 10, nl);

        return cityBuilding;
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
