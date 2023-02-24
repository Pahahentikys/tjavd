package com.company.marketer.service.impl;

import com.company.marketer.domain.CompanyInfo;
import com.company.marketer.enums.CompanyName;
import com.company.marketer.repository.CompanyInfoRepository;
import com.company.marketer.service.CompanyInfoService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyInfoServiceImpl implements CompanyInfoService {
    Logger logger = LogManager.getLogger(CompanyInfoServiceImpl.class);

    private final CompanyInfoRepository companyInfoRepository;

    @Override
    public Flux<CompanyInfo> saveAll(List<CompanyInfo> companyInfos) {
        return companyInfoRepository.saveAll(companyInfos);
    }

    @Override
    public Mono<CompanyInfo> findLastByName(@NonNull String name) {
        return companyInfoRepository.findLastByName(name);
    }

    @Override
    public Mono<CompanyInfo> findByNameAndDate(@NonNull String name, @NonNull LocalDate date) {
        logger.info("[CompanyInfoServiceImpl.findByNameAndDate]: Starting of search company info into db by name=%s and date=%s".formatted(name, date));
        return companyInfoRepository.findByNameAndDate(name, date)
                .doOnSuccess(ci -> logger.info("[CompanyInfoServiceImpl.findByNameAndDate] Found company info data"));
    }

    @Override
    public Mono<Void> storeDataWithFilteringOnExistingInfo(@NonNull List<CompanyInfo> listOfCompanies, @NonNull CompanyName companyName) {
        logger.info("[CompanyInfoServiceImpl.storeDataWithFilteringOnExistingInfo] Started try to storing json parsed data into db for company: %s".formatted(companyName));

        findLastByName(companyName.name())
                .blockOptional()
                .ifPresentOrElse(ci -> {
                            var lastDateInfo = ci.getDate();

                            for (int i = 0; i < listOfCompanies.size(); i++) {
                                var companyInfo = listOfCompanies.get(i);

                                if (companyInfo.getDate().isAfter(lastDateInfo)) {
                                    var newCompaniesForPersist = listOfCompanies.subList(i, listOfCompanies.size());

                                    saveAll(newCompaniesForPersist)
                                            .doOnComplete(() -> logger.info("[CompanyInfoServiceImpl.storeDataWithFilteringOnExistingInfo] Completed storing json parsed data into db for company: %s".formatted(companyName)))
                                            .subscribe();

                                    break;
                                }
                            }
                        },
                        () -> saveAll(listOfCompanies)
                                .doOnComplete(() -> logger.info("[CompanyInfoServiceImpl.storeDataWithFilteringOnExistingInfo] Completed storing json parsed data into db for company: %s".formatted(companyName)))
                                .subscribe());

        return Mono.empty();
    }
}
