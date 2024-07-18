package com.Natwest.task.service;

import com.Natwest.task.vo.FileType;
import com.Natwest.task.vo.ReportGenerationVO;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

public interface FileProcessor {

    FileType getFileType();

    Map<String, Map<String, String>> getMapOfFieldsWithJoinCondition(ReportGenerationVO reportGenerationVO);

    Iterator<Map<String, String>> getIterator(ReportGenerationVO reportGenerationVO);

}
