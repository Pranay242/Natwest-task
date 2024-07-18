package com.Natwest.task.controller;

import com.Natwest.task.service.ReportGenerationService;
import com.Natwest.task.service.ReportGenerator;
import com.Natwest.task.vo.ReportGenerationVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class MainController {

    @Autowired
    private ReportGenerator reportGenerator;

    Logger logger = LoggerFactory.getLogger(MainController.class);
    @GetMapping("/request")
    public String registerRequest() {
        reportGenerator.execute();
        return "SUCCESS";
    }
}
