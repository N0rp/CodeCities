package parse.csv;

/**
 * Created by Richard on 7/12/2015.
 */
public class CsvParseException extends Exception{

    public CsvParseException(int index, String actualValue, String expectedValue){
        super(CsvParseException.createExpectedHeaderValueMessage(index, actualValue, expectedValue));
    }

    public CsvParseException(String row, HeaderEnum headerEnum){
        super(CsvParseException.createRowMissingValueMessage(row, headerEnum));
    }

    public CsvParseException(String row, HeaderEnum headerEnum, String value, Class expectedType){
        super(CsvParseException.createExpectedTypeMessage(row, headerEnum, value, expectedType));
    }

    private static String createRowMissingValueMessage(String row, HeaderEnum headerEnum){
        String message = String.format("The string in row %s with enum %s is missing.",
                row, headerEnum);
        return message;
    }

    private static String createExpectedHeaderValueMessage(int index, String actualValue, String expectedValue){
        String message = String.format("The string in the header at index %d with value %s was supposed to have the value %s.",
                index, actualValue, expectedValue);
        return message;
    }

    private static String createExpectedTypeMessage(String row, HeaderEnum headerEnum, String value, Class expectedType){
        String message = String.format("The string in row %s with enum %s with value %s had the wrong type. Expected %s.",
                row, headerEnum, value, expectedType);
        return message;
    }
}
