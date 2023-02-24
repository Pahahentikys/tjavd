package com.company.marketer.scheduler;

import com.company.marketer.enums.CompanyName;
import com.company.marketer.service.ImportJsonDataService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Description;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class SchedulerJobs {
    Logger logger = LogManager.getLogger(SchedulerJobs.class);

    private final ImportJsonDataService importJsonDataService;


    @Description("Import json data in data base")
    @Scheduled(cron = "${marketer.scheduler.cron.import-json}")
    public void importJsonInfoIntoDataBase() {
        Arrays.stream(CompanyName.values()).forEach(cn -> {
            logger.info("[SchedulerJobs.importJsonInfoIntoDataBase] Started scheduled import json data for company: %s".formatted(cn));

            importJsonDataService.importDataByCompanyName(cn)
                    .doOnSuccess(d -> logger.info("[SchedulerJobs.importJsonInfoIntoDataBase] Ended scheduled import json data for company: %s".formatted(cn)))
                    .blockOptional();
        });
    }
}
