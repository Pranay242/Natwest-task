package com.Natwest.task.service;

import com.Natwest.task.bean.NatwestConfiguration;
import com.Natwest.task.vo.ReportGenerationVO;

import java.util.List;

public interface ReportGenerationService {

    void generateReport(ReportGenerationVO reportGenerationVO);

    List<ReportGenerationVO> getFilesFromLocalForReportGenerationVO();

    NatwestConfiguration getConfiguration();
}
