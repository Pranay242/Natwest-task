package com.Natwest.task.service;

import com.Natwest.task.bean.FieldInfo;
import com.Natwest.task.bean.FileMetadata;
import com.Natwest.task.vo.FileType;
import com.Natwest.task.writer.NatwestFileWriter;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class JsonFileProcessor implements FileProcessor {
    @Override
    public FileType getFileType() {
        return FileType.JSON;
    }

    @Override
    public Map<String, Map<String, String>> getMapOfFieldsWithJoinCondition(String fileName, List<FieldInfo> joinCondition) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public Iterator<Map<String, String>> getIterator(String fileName) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public NatwestFileWriter getFileWriter(String fileName, FileMetadata fileMetadata) {
        throw new RuntimeException("Not implemented yet");
    }
}
