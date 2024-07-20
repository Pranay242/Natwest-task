package com.Natwest.task.iterator;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.input.BOMInputStream;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Slf4j
public class CSVIterator implements Iterator<Map<String, String>> {

    private static final String UTF = "UTF-8";
    private InputStream inputStream;
    private BOMInputStream bomInputStream;
    private InputStreamReader reader;
    private CSVReader csvReader;
    private String[] headers;

    private boolean hasNext = true;

    public CSVIterator(String fileName) throws CsvValidationException, IOException {
        try {
            inputStream = new FileInputStream(fileName);
            bomInputStream = new BOMInputStream(inputStream);
            reader = new InputStreamReader(new BufferedInputStream(bomInputStream), UTF);
            csvReader = new CSVReader(reader);
            headers = csvReader.readNext();
            if(csvReader.peek() == null) {
                hasNext = false;
                closeAll();
            }
        } catch(Exception e) {
            log.error("Exception occurred while reading CSV file");
            throw e;
        }
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @SneakyThrows
    @Override
    public Map<String, String> next() {
        Map<String, String> nextRow = new HashMap<>();
        String[] next = csvReader.readNext();
        for (int i = 0; i < headers.length; i++) {
            nextRow.put(headers[i], next[i]);
        }
        if(csvReader.peek() == null) {
            hasNext = false;
            closeAll();
        }
        return nextRow;
    }

    /**
     * Closes all open connections
     * @throws IOException
     */
    private void closeAll() throws IOException {
        this.inputStream.close();
        this.bomInputStream.close();
        this.reader.close();
        this.csvReader.close();
    }
}
