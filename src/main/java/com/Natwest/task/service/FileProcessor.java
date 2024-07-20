package com.Natwest.task.service;

import com.Natwest.task.bean.FieldInfo;
import com.Natwest.task.bean.FileMetadata;
import com.Natwest.task.vo.FileType;
import com.Natwest.task.writer.NatwestFileWriter;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public interface FileProcessor {

    FileType getFileType();

    Map<String, Map<String, String>> getMapOfFieldsWithJoinCondition(String fileName, List<FieldInfo> joinCondition);

    Iterator<Map<String, String>> getIterator(String fileName);

    NatwestFileWriter getFileWriter(String fileName, FileMetadata fileMetadata);

}
