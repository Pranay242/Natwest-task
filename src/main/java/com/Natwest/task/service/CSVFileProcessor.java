package com.Natwest.task.service;

import com.Natwest.task.vo.FileType;
import com.Natwest.task.vo.ReportGenerationVO;

import java.util.Iterator;
import java.util.Map;

public class CSVFileProcessor implements FileProcessor {


    @Override
    public Map<String, Map<String, String>> getMapOfFieldsWithJoinCondition(ReportGenerationVO reportGenerationVO) {
        //TODO
        return null;
    }

    @Override
    public Iterator<Map<String, String>> getIterator(ReportGenerationVO reportGenerationVO) {

        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Map<String, String> next() {
                return null;
            }
        };
    }

    @Override
    public FileType getFileType() {
        return FileType.CSV;
    }
}
