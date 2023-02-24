package com.company.marketer.service.impl;

import com.company.marketer.domain.CompanyInfo;
import com.company.marketer.domain.ParsedJsonInfo;
import com.company.marketer.enums.CompanyName;
import com.company.marketer.service.CompanyInfoService;
import com.company.marketer.service.ImportJsonDataService;
import com.company.marketer.service.JsonProccessingService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImportJsonDataServiceImpl implements ImportJsonDataService {
    private final Logger logger = LogManager.getLogger(ImportJsonDataServiceImpl.class);

    private static final String JSON_EXT = ".json";

    private final JsonProccessingService jsonProccessingService;

    private final CompanyInfoService companyInfoService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Mono<Void> importDataByCompanyName(@NonNull CompanyName companyName) {
        logger.info("[ImportJsonDataServiceImpl.importDataByCompanyName]: Json import data into DB started for company with name: %s".formatted(companyName));

        return jsonProccessingService.parseJsonFile(makeFileNameWithExtension(companyName))
                .flatMap(pi -> {
                    var listOfCompanies = getListOfCompanyInfo(pi, companyName);
                    return companyInfoService.storeDataWithFilteringOnExistingInfo(listOfCompanies, companyName);
                })
                .doOnSuccess(i -> logger.info("[ImportJsonDataServiceImpl.importDataByCompanyName]: Json import data into DB completed for company with name: %s".formatted(companyName)))
                .onErrorResume(ex -> Mono.error(new IllegalArgumentException("Json parsing to ParsedJsonInfo entity error", ex)));
    }

    private List<CompanyInfo> getListOfCompanyInfo(@NonNull ParsedJsonInfo parsedJsonInfo, @NonNull CompanyName companyName) {
        var dateTimes = parsedJsonInfo.getDateTimes();

        var lowPrices = parsedJsonInfo.getLowPrices();
        var closePrices = parsedJsonInfo.getClosePrices();
        var highPrices = parsedJsonInfo.getHighPrices();

        var companyInfoList = new ArrayList<CompanyInfo>();

        for (int i = 0; i < dateTimes.size(); i++) {
            companyInfoList.add(new CompanyInfo(
                    UUID.randomUUID(),
                    dateTimes.get(i).toLocalDate(),
                    companyName.name(),
                    closePrices.get(i),
                    highPrices.get(i),
                    lowPrices.get(i)));
        }

        return companyInfoList;
    }

    private String makeFileNameWithExtension(CompanyName companyName) {
        return companyName.name() + JSON_EXT;
    }
}
