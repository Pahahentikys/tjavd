package com.company.marketer.service;


import com.company.marketer.domain.CompanyInfo;
import com.company.marketer.enums.CompanyName;
import lombok.NonNull;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CompanyInfoService {
    Flux<CompanyInfo> saveAll(List<CompanyInfo> companyInfos);

    Mono<CompanyInfo> findLastByName(@NonNull String name);

    Mono<Void> storeDataWithFilteringOnExistingInfo(@NonNull List<CompanyInfo> listOfCompanies, @NonNull CompanyName companyName);
}
