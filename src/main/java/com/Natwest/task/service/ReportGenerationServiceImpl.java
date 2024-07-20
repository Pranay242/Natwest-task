package com.Natwest.task.service;

import com.Natwest.task.bean.FieldInfo;
import com.Natwest.task.bean.JoinMetadata;
import com.Natwest.task.bean.NatwestConfiguration;
import com.Natwest.task.utils.ArithmeticUtils;
import com.Natwest.task.vo.FileType;
import com.Natwest.task.vo.ReportGenerationVO;
import com.Natwest.task.writer.NatwestFileWriter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class ReportGenerationServiceImpl implements ReportGenerationService {

    private Map<FileType, FileProcessor> fileTypeFileProcessorMap = new HashMap<>();

    @Autowired
    public ReportGenerationServiceImpl(List<FileProcessor> fileProcessors) {
        for (FileProcessor fileProcessor : fileProcessors) {
            fileTypeFileProcessorMap.put(fileProcessor.getFileType(), fileProcessor);
        }
    }

    @SneakyThrows
    @Override
    public void generateReport(ReportGenerationVO reportGenerationVO) {
        log.info("starting generating report for id : {}", reportGenerationVO.getId());
        Date startDate = new Date();

        NatwestConfiguration natwestConfiguration = reportGenerationVO.getNatwestConfiguration();

        String referenceFilePath = reportGenerationVO.getReferenceFilePath();
        FileType referenceFileType = reportGenerationVO.getReferenceExtension() == null ? reportGenerationVO.getInputExtension() : reportGenerationVO.getReferenceExtension();
        FileProcessor referenceFileProcessor = fileTypeFileProcessorMap.get(referenceFileType);

        List<FieldInfo> refJoinCondition = new ArrayList<>();
        List<FieldInfo> inputJoinCondition = new ArrayList<>();
        // Reference file join key fields, refKey1, refKey2
        for (JoinMetadata joinMetadata : natwestConfiguration.getJoin()) {
            refJoinCondition.add(joinMetadata.getReferenceField());
        }
        // Input join key fields, refKey1, refKey2
        for (JoinMetadata joinMetadata : natwestConfiguration.getJoin()) {
            inputJoinCondition.add(joinMetadata.getInputField());
        }

        Map<String, Map<String, String>> referenceJoinData = referenceFileProcessor.getMapOfFieldsWithJoinCondition(referenceFilePath, refJoinCondition);

        FileProcessor inputFileProcessor = fileTypeFileProcessorMap.get(reportGenerationVO.getInputExtension());
        Iterator<Map<String, String>> inputIterator = inputFileProcessor.getIterator(reportGenerationVO.getInputFilePath());

        FileProcessor outputFileProcessor = fileTypeFileProcessorMap.get(reportGenerationVO.getOutputExtension());
        NatwestFileWriter natwestFileWriter = outputFileProcessor.getFileWriter(reportGenerationVO.getOutputFilePath(), natwestConfiguration.getOutput());

        while (inputIterator.hasNext()) {
            Map<String, String> inputRow = inputIterator.next();
            StringBuilder key = new StringBuilder();
            for (FieldInfo fieldInfo : inputJoinCondition) {
                key.append(inputRow.get(fieldInfo.getFieldName())).append("::");
            }
            Map<String, String> refRow = referenceJoinData.get(key.toString());
            String[] rowToWrite = getRowToWriteAfterTransformation(inputRow, refRow, natwestConfiguration);
            natwestFileWriter.writeRow(rowToWrite);
        }

        natwestFileWriter.closeAll();

        Date endDate = new Date();
        log.info("Ended generating report for id : {} time taken - {} seconds", reportGenerationVO.getId(), (endDate.getTime() - startDate.getTime()) / 1000);
    }


    private String[] getRowToWriteAfterTransformation(Map<String, String> inputRow, Map<String, String> refRow, NatwestConfiguration natwestConfiguration) throws Exception {

        String[] row = new String[natwestConfiguration.getOutput().getFields().size()];
        if(refRow == null || inputRow == null) {
            return row;
        }

        // Adding inputRow and refRow in one map for transformation, alias is used to avoid conflict in column name
        Map<String, String> commonMap = new HashMap<>();
        for (Map.Entry<String, String> entry : inputRow.entrySet()) {
            commonMap.put(natwestConfiguration.getInput().getAlias() + "." + entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, String> entry : refRow.entrySet()) {
            commonMap.put(natwestConfiguration.getReference().getAlias() + "." + entry.getKey(), entry.getValue());
        }

        // Calculate the output row for every output field
        int i = 0;
        for (FieldInfo fieldInfo : natwestConfiguration.getOutput().getFields()) {
            String value = null;
            if(fieldInfo.getFormula().getOperationType().equals("arithmetic")) {
                String expression = fieldInfo.getFormula().getExpression();
                for (String key : commonMap.keySet()) {
                    expression = expression.replace(key, commonMap.get(key));
                }
                try {
                    value = String.valueOf(ArithmeticUtils.evaluate(expression));
                } catch (Exception e) {
                    // If ArithmeticUtils throws exception then value will be null
                    log.error("Error while calculating expression : {}", expression);
                }
            } else if(fieldInfo.getFormula().getOperationType().equals("assignment")){
                value = commonMap.get(fieldInfo.getFormula().getExpression());
            }
            // You can add many operation type here, as per requirement

            row[i++] = value;
        }
        return row;

    }

    @Override
    public List<ReportGenerationVO> getFilesFromLocalForReportGenerationVO() {
        List<ReportGenerationVO> reportGenerationVOS = readVOFromPath("config/fileSet.json");
        return reportGenerationVOS;
    }

    @Override
    public NatwestConfiguration getConfiguration() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new File("config/configuration.json"), new TypeReference<NatwestConfiguration>(){});
        } catch (IOException e) {
            log.error("Error while reading config files");
            throw new RuntimeException(e);
        }
    }

    private List<ReportGenerationVO> readVOFromPath(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new File(filePath), new TypeReference<List<ReportGenerationVO>>(){});
        } catch (IOException e) {
            log.error("Error while reading local files");
            throw new RuntimeException(e);
        }
    }
}
