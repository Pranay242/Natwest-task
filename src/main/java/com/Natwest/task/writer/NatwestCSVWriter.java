package com.Natwest.task.writer;

import com.Natwest.task.bean.FieldInfo;
import com.Natwest.task.bean.FileMetadata;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class NatwestCSVWriter implements NatwestFileWriter {

    private CSVWriter csvWriter;
    private String[] headers;
    FileWriter fileWriter;

    public NatwestCSVWriter(String fileName, FileMetadata fileMetadata) {
        headers = new String[fileMetadata.getFields().size()];
        int i = 0;
        for (FieldInfo field : fileMetadata.getFields()) {
            headers[i++] = field.getFieldName();
        }
        try {
            File file = new File(fileName);
            if(file.exists()) {
                file.delete();
            }
            file.createNewFile();
            this.fileWriter = new FileWriter(fileName);
            this.csvWriter = new CSVWriter(fileWriter);
            csvWriter.writeNext(headers);
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    @Override
    public void writeRow(String[] row) {
        csvWriter.writeNext(row);
    }

    @Override
    public void closeAll() throws IOException {
        csvWriter.flush();
        csvWriter.close();
    }
}
