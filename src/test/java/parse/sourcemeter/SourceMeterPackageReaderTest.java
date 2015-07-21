package parse.sourcemeter;

import city.CityPackage;
import org.junit.Assert;
import org.junit.Test;
import parse.csv.CsvParseException;
import parse.csv.CsvParserTest;

import java.util.List;

/**
 * Created by Richard on 7/21/2015.
 */
public class SourceMeterPackageReaderTest {


    @Test
    public void testParseClass() throws CsvParseException {
        SourceMeterPackageReader sourceMeterPackageReader = new SourceMeterPackageReader(
                CsvParserTest.PATH_SOURCE_METER_PACKAGE,
                CsvParserTest.PATH_SOURCE_METER_CLASS,
                CsvParserTest.PATH_SOURCE_METER_METHOD);
        List<CityPackage> cityPackages = sourceMeterPackageReader.createCityPackages();
        Assert.assertNotNull(cityPackages);
        Assert.assertNotEquals(0, cityPackages.size());


    }
}
