package com.Natwest.task.service;

import com.Natwest.task.vo.ReportGenerationVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ReportGenerator {

    @Autowired
    ReportGenerationService reportGenerationService;

    public void execute() {
        log.info("Batch execution started ");
        long startTime = new Date().getTime();

        // There is no upstream source configured, but can be used here to fetch data, and fill up the VO
        List<ReportGenerationVO> reportGenerationVOS = reportGenerationService.getFilesFromLocalForReportGenerationVO();
        for (ReportGenerationVO reportGenerationVO : reportGenerationVOS) {
            reportGenerationService.generateReport(reportGenerationVO);
        }

        log.info("batch is finished time taken - {}", new Date().getTime() - startTime);

    }
}
