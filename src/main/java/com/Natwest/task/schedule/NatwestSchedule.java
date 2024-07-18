package com.Natwest.task.schedule;

import com.Natwest.task.service.ReportGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class NatwestSchedule {

    @Autowired
    ReportGenerator reportGenerator;

    @Scheduled(cron = "${spring.cron}")
    public void execute() {
        reportGenerator.execute();
    }
}
