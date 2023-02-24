package com.company.marketer.scheduler;

import com.company.marketer.enums.CompanyName;
import com.company.marketer.service.ImportJsonDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class SchedulerJobs {
    private final ImportJsonDataService importJsonDataService;


    @Description("Import json data in data base")
    @Scheduled(cron = "${marketer.scheduler.cron.import-json}")
    public void importJsonInfoIntoDataBase() {
        Arrays.stream(CompanyName.values()).forEach(cn -> importJsonDataService.importDataByCompanyName(cn).blockOptional());
    }
}
