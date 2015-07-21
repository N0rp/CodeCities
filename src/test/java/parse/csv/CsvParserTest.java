package parse.csv;

import org.junit.Assert;
import org.junit.Test;
import parse.csv.HeaderEnum;
import parse.csv.CsvParseException;
import parse.csv.CsvParser;
import parse.sourcemeter.SourceMeterMappings;

import java.util.List;
import java.util.Map;

/**
 * Created by Richard on 7/10/2015.
 */
public class CsvParserTest {

    @Test
    public void testParseMethod() throws CsvParseException {
        CsvParser csvParser = new CsvParser();
        List<Map<HeaderEnum, String>> methodMappings = null;
        methodMappings = csvParser.parse("src/test/resources/log4j-1.2.17-Method.csv", SourceMeterMappings.methodMappings);
        Assert.assertNotNull(methodMappings);
        Assert.assertNotEquals(0, methodMappings.size());
    }


    @Test
    public void testParseClass() throws CsvParseException {
        CsvParser csvParser = new CsvParser();
        List<Map<HeaderEnum, String>> classMappings = null;
        classMappings = csvParser.parse("src/test/resources/log4j-1.2.17-Class.csv", SourceMeterMappings.classMappings);
        Assert.assertNotNull(classMappings);
        Assert.assertNotEquals(0, classMappings.size());
    }
}
