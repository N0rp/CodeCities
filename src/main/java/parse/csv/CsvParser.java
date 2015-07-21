package parse.csv;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Richard on 7/10/2015.
 */
public class CsvParser {

    public List<Map<HeaderEnum, String>> parse(String filePath, MappingTuple[] mappingTuples) throws CsvParseException {
        File file = new File(filePath);
        if(file.exists()){
            return parseLinesInFile(file, mappingTuples);
        }else{
            System.err.println("Cannot find file: "+file.getAbsolutePath());
            return new LinkedList<>();
        }
    }

    private List<Map<HeaderEnum, String>> parseLinesInFile(File file, MappingTuple[] mappingTuples) throws CsvParseException {
        List<Map<HeaderEnum, String>> fileMap = new LinkedList<>();
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        try {
            br = new BufferedReader(new FileReader(file));
            boolean isHeader = true;
            int row = 0;
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] split = line.split(cvsSplitBy);
                if(!line.isEmpty()) {
                    if (isHeader) {
                        isHeader = false;
                        verifyHeader(split, mappingTuples);
                    } else if (!isHeader) {
                        Map<HeaderEnum, String> lineMap = new HashMap<>();
                        for(MappingTuple tuple : mappingTuples){
                            HeaderEnum headerEnum = tuple.headerEnum;
                            int index = tuple.index;
                            lineMap.put(headerEnum, split[index]);
                        }
                        if(lineMap.size() > 0){
                            fileMap.add(lineMap);
                        }
                    }
                }
                row++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Done");

        return fileMap;
    }

    private double getDouble(int row, String[] array, int index) throws CsvParseException {
        double result = -1;
        try{
            String str = array[index].replace("\"", "");
            result = Double.parseDouble(str);
        }catch (NumberFormatException e){
            throw new CsvParseException(row, index, array[index], Integer.class);
        }

        return result;
    }

    private String getString(String[] array, int index){
        String str = array[index].replace("\"", "");
        return str;
    }


    private void verifyHeader(String[] header, MappingTuple[] mappingTuples) throws CsvParseException {
        for (MappingTuple pair : mappingTuples){
            if(!header[pair.index].equals(pair.name)){
                throw new CsvParseException(pair.index, header[pair.index], pair.name);
            }
        }
    }

}
