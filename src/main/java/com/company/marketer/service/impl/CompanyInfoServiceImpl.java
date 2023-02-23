package com.company.marketer.service.impl;

import com.company.marketer.domain.CompanyInfo;
import com.company.marketer.repository.CompanyInfoRepository;
import com.company.marketer.service.CompanyInfoService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyInfoServiceImpl implements CompanyInfoService {
    private final CompanyInfoRepository companyInfoRepository;

    @Override
    public Flux<CompanyInfo> saveAll(List<CompanyInfo> companyInfos) {
        return companyInfoRepository.saveAll(companyInfos);
    }

    @Override
    public Mono<CompanyInfo> findLastByName(@NonNull String name) {
        return companyInfoRepository.findLastByName(name);
    }
}
