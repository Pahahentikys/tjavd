package com.company.marketer.service;

import com.company.marketer.domain.ParsedJsonInfo;
import com.company.marketer.enums.CompanyName;
import com.company.marketer.service.impl.ImportJsonDataServiceImpl;
import com.company.marketer.service.impl.JsonProccessingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@DisplayName("Importing json data in db tests")
public class ImportJsonDataServiceImplTest {
    private JsonProccessingService jsonProccessingService;

    private ImportJsonDataService importJsonDataService;

    private CompanyInfoService companyInfoService;


    @BeforeEach
    void setUp() {
        jsonProccessingService = mock(JsonProccessingServiceImpl.class);

        companyInfoService = mock(CompanyInfoService.class);

        importJsonDataService = new ImportJsonDataServiceImpl(jsonProccessingService, companyInfoService);
    }

    @Test
    @DisplayName("Test for method for importing data from json in db")
    void importDataByCompanyNameTest() {
        var lowPrices = List.of(new BigDecimal("19.100000381469727"));
        var highPrices = List.of(new BigDecimal("21.399999618530273"));
        var closePrices = List.of(new BigDecimal("19.299999237060547"));

        var parsedJsonInfo = new ParsedJsonInfo(
                List.of(ZonedDateTime.parse("2005-09-27T13:30Z[UTC]")),
                lowPrices,
                highPrices,
                closePrices);

        when(jsonProccessingService.parseJsonFile(any())).thenReturn(Mono.just(parsedJsonInfo));
        when(companyInfoService.storeDataWithFilteringOnExistingInfo(anyList(), any())).thenReturn(Mono.empty());

        importJsonDataService.importDataByCompanyName(CompanyName.AAL).blockOptional();

        verify(jsonProccessingService).parseJsonFile(any());
        verify(companyInfoService).storeDataWithFilteringOnExistingInfo(anyList(), any());
    }
}
