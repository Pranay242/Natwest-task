package com.Natwest.task.service;

import com.Natwest.task.bean.FieldInfo;
import com.Natwest.task.bean.FileMetadata;
import com.Natwest.task.iterator.CSVIterator;
import com.Natwest.task.vo.FileType;
import com.Natwest.task.writer.NatwestCSVWriter;
import com.Natwest.task.writer.NatwestFileWriter;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class CSVFileProcessor implements FileProcessor {


    /**
     * Return the Map of rows, key as joinCondition
     */
    @Override
    public Map<String, Map<String, String>> getMapOfFieldsWithJoinCondition(String fileName, List<FieldInfo> joinCondition) {
        Iterator<Map<String, String>> iterator = getIterator(fileName);
        Map<String, Map<String, String>> returnMap = new HashMap<>();
        while (iterator.hasNext()) {
            Map<String, String> row = iterator.next();
            StringBuilder key = new StringBuilder();
            for (FieldInfo fieldInfo : joinCondition) {
                key.append(row.get(fieldInfo.getFieldName())).append("::");
            }
            returnMap.put(key.toString(), row);
        }
        return returnMap;
    }

    @Override
    public Iterator<Map<String, String>> getIterator(String fileName) {
        Iterator<Map<String, String>> iterator = null;
        try {
            iterator = new CSVIterator(fileName);
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return iterator;
    }

    @Override
    public NatwestFileWriter getFileWriter(String fileName, FileMetadata fileMetadata) {
        return new NatwestCSVWriter(fileName, fileMetadata);
    }

    @Override
    public FileType getFileType() {
        return FileType.CSV;
    }
}
