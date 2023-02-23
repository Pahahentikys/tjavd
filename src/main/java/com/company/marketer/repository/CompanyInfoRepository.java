package com.company.marketer.repository;


import com.company.marketer.domain.CompanyInfo;
import lombok.NonNull;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Repository
public interface CompanyInfoRepository extends ReactiveCassandraRepository<CompanyInfo, UUID> {

    @AllowFiltering
    Flux<List<CompanyInfo>> findByName(@NonNull String name);

    @AllowFiltering
    @Query(value = "select * from app_space.company_info" +
            " where name = :name " +
            " limit 1")
    Mono<CompanyInfo> findLastByName(@NonNull String name);
}
