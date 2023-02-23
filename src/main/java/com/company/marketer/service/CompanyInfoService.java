package com.company.marketer.service;


import com.company.marketer.domain.CompanyInfo;
import lombok.NonNull;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CompanyInfoService {
    Flux<CompanyInfo> saveAll(List<CompanyInfo> companyInfos);

    Mono<CompanyInfo> findLastByName(@NonNull String name);
}
