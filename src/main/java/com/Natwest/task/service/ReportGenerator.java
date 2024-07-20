package com.Natwest.task.service;

import com.Natwest.task.bean.FieldInfo;
import com.Natwest.task.bean.JoinMetadata;
import com.Natwest.task.bean.NatwestConfiguration;
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

        NatwestConfiguration natwestConfiguration = reportGenerationService.getConfiguration();
        populateFileAlias(natwestConfiguration);

        // There is no upstream source configured, but can be used here to fetch data, and fill up the VO
        // But here, local files are used
        //List of vos(vo1,vo2,vo2)
        List<ReportGenerationVO> reportGenerationVOS = reportGenerationService.getFilesFromLocalForReportGenerationVO();
        for (ReportGenerationVO reportGenerationVO : reportGenerationVOS) {
            reportGenerationVO.setNatwestConfiguration(natwestConfiguration);
            reportGenerationService.generateReport(reportGenerationVO);
        }

        log.info("batch is finished time taken - {} seconds", (new Date().getTime() - startTime) / 1000);

    }

    private void populateFileAlias(NatwestConfiguration natwestConfiguration) {
        for (FieldInfo field : natwestConfiguration.getInput().getFields()) {
            field.setFileAlias(natwestConfiguration.getInput().getAlias());
        }
        for (FieldInfo field : natwestConfiguration.getOutput().getFields()) {
            field.setFileAlias(natwestConfiguration.getOutput().getAlias());
        }
        for (FieldInfo field : natwestConfiguration.getReference().getFields()) {
            field.setFileAlias(natwestConfiguration.getReference().getAlias());
        }

        for (JoinMetadata joinMetadata : natwestConfiguration.getJoin()) {
            joinMetadata.getInputField().setFileAlias(natwestConfiguration.getInput().getAlias());
            joinMetadata.getReferenceField().setFileAlias(natwestConfiguration.getReference().getAlias());
        }
    }
}
