package com.company.marketer.service;


import com.company.marketer.domain.CompanyInfo;
import reactor.core.publisher.Flux;

import java.util.List;

public interface CompanyInfoService {
    Flux<CompanyInfo> saveAll(List<CompanyInfo> companyInfos);
}
