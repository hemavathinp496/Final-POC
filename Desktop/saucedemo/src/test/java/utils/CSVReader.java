package utils;

import java.io.BufferedReader;


import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    public static Object[][] readCSV(String filePath) throws IOException {

        List<String[]> rows = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(filePath));

        String line;
        boolean firstLine = true;

        while ((line = br.readLine()) != null) {

            if (firstLine) {
                firstLine = false;
                continue;
            }

            String[] columns = line.split(",");
            rows.add(columns);
        }

        br.close();

    
        Object[][] data = new Object[rows.size()][];
        for (int i = 0; i < rows.size(); i++) {
            data[i] = rows.get(i);
        }

        return data;
    }
}
