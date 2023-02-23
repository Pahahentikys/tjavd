package com.company.marketer.service.impl;

import com.company.marketer.domain.CompanyInfo;
import com.company.marketer.domain.ParsedJsonInfo;
import com.company.marketer.enums.CompanyName;
import com.company.marketer.service.CompanyInfoService;
import com.company.marketer.service.ImportJsonDataService;
import com.company.marketer.service.JsonProccessingService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImportJsonDataServiceImpl implements ImportJsonDataService {
    private static final String JSON_EXT = ".json";

    private final JsonProccessingService jsonProccessingService;

    private final CompanyInfoService companyInfoService;

    @Override
    public void importDataByCompanyName(@NonNull CompanyName companyName) {
        var parsedInfo = jsonProccessingService.parseJsonFile(makeFileNameWithExtension(companyName));

        var listOfCompanies = getListOfCompanyInfo(parsedInfo, companyName);

        companyInfoService.findLastByName(companyName.name())
                .blockOptional()
                .ifPresentOrElse(ci -> {
                            var lastDateInfo = ci.getDate();

                            for (int i = 0; i < listOfCompanies.size(); i++) {
                                var companyInfo = listOfCompanies.get(i);

                                if (companyInfo.getDate().isAfter(lastDateInfo)) {
                                    var newCompaniesForPersist = listOfCompanies.subList(i, listOfCompanies.size());

                                    companyInfoService.saveAll(newCompaniesForPersist).subscribe();

                                    break;
                                }
                            }
                        },
                        () -> companyInfoService.saveAll(listOfCompanies).subscribe());
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
