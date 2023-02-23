package com.company.marketer.service;


import com.company.marketer.enums.CompanyName;
import lombok.NonNull;

public interface ImportJsonDataService {
   void importDataByCompanyName(@NonNull CompanyName companyName);
}
