package com.company.marketer.web;


import com.company.marketer.enums.CompanyName;
import com.company.marketer.service.ImportJsonDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/import-json")
public class ImportJsonDataController {
    private final ImportJsonDataService importJsonDataService;

    @PostMapping("/by-company-name/{companyName}")
    public Mono<Void> importJson(@PathVariable("companyName") CompanyName companyName) {
        return importJsonDataService.importDataByCompanyName(companyName);
    }
}
