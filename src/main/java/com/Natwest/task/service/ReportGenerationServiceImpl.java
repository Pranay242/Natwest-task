package com.Natwest.task.service;

import com.Natwest.task.vo.ReportGenerationVO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ReportGenerationServiceImpl implements ReportGenerationService {
    @Override
    public void generateReport(ReportGenerationVO reportGenerationVO) {
        log.info("starting generating report for id : {}", reportGenerationVO.getId());
        Date startDate = new Date();

        //Logic



        Date endDate = new Date();
        log.info("Ended generating report for id : {} time taken - {} seconds", reportGenerationVO.getId(), (endDate.getTime() - startDate.getTime()) / 1000);
    }

    @Override
    public List<ReportGenerationVO> getFilesFromLocalForReportGenerationVO() {
        List<ReportGenerationVO> reportGenerationVOS = readVOFromPath("D:\\Natwest task\\Natwest-task\\src\\main\\resources\\sampleFiles.json");
        return reportGenerationVOS;
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
