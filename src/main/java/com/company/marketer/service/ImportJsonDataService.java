package com.company.marketer.service;


import com.company.marketer.enums.CompanyName;
import lombok.NonNull;
import reactor.core.publisher.Mono;

public interface ImportJsonDataService {
   Mono<Void> importDataByCompanyName(@NonNull CompanyName companyName);
}
