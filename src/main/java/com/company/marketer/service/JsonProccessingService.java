package com.company.marketer.service;

import com.company.marketer.domain.ParsedJsonInfo;
import lombok.NonNull;
import reactor.core.publisher.Mono;

public interface JsonProccessingService {

    Mono<ParsedJsonInfo> parseJsonFile(@NonNull String jsonFileName);
}
