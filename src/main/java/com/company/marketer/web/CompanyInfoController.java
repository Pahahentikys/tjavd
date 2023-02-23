package com.company.marketer.web;

import com.company.marketer.domain.CompanyInfo;
import com.company.marketer.service.CompanyInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/company-info")
public class CompanyInfoController {
    private final CompanyInfoService companyInfoService;

    @GetMapping("/by-name-and-date")
    public Mono<CompanyInfo> getClientById(@RequestParam("name") String name,
                                           @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return companyInfoService.findByNameAndDate(name, date);
    }
}
