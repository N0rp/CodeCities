package parse.csv;

/**
 * Created by Richard on 7/12/2015.
 */
public class CsvParseException extends Exception{

    public CsvParseException(int index, String actualValue, String expectedValue){
        super(CsvParseException.createExpectedHeaderValueMessage(index, actualValue, expectedValue));
    }

    public CsvParseException(int row, int index, String value, Class expectedType){
        super(CsvParseException.createExpectedTypeMessage(row, index, value, expectedType));
    }

    private static String createExpectedHeaderValueMessage(int index, String actualValue, String expectedValue){
        String message = String.format("The string in the header at index %d with value %s was supposed to have the value %s.",
                index, actualValue, expectedValue);
        return message;
    }

    private static String createExpectedTypeMessage(int row, int index, String value, Class expectedType){
        String message = String.format("The string in row %d at index %d with value %s had the wrong type. Expected %s.",
                row, index, value, expectedType);
        return message;
    }
}
