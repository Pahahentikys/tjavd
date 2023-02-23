package com.company.marketer.repository;


import com.company.marketer.domain.CompanyInfo;
import lombok.NonNull;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;

@Repository
public interface CompanyInfoRepository extends ReactiveCassandraRepository<CompanyInfo, UUID> {

    @AllowFiltering
    Flux<List<CompanyInfo>> findByName(@NonNull String name);
}
