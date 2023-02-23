package com.company.marketer.service;

import com.company.marketer.enums.CompanyName;
import com.company.marketer.service.impl.ImportJsonDataServiceImpl;
import com.company.marketer.service.impl.JsonProccessingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Importing json data in db tests")
public class ImportJsonDataServiceImplTest {
    private JsonProccessingService jsonProccessingService;

    private ImportJsonDataService importJsonDataService;

    @BeforeEach
    void setUp() {
        jsonProccessingService = new JsonProccessingServiceImpl();

        importJsonDataService = new ImportJsonDataServiceImpl(jsonProccessingService);
    }

    @Test
    @DisplayName("Test for method for importing data from json in db")
    void importDataByCompanyNameTest(){
        importJsonDataService.importDataByCompanyName(CompanyName.AAPL);
    }
}
