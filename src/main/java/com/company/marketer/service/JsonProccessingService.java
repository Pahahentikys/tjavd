package com.company.marketer.service;

import com.company.marketer.domain.ParsedJsonInfo;
import lombok.NonNull;

public interface JsonProccessingService {

    @NonNull
    ParsedJsonInfo getParsedInfo(@NonNull String jsonFileName);
}
