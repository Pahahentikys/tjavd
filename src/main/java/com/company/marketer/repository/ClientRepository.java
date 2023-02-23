package com.company.marketer.repository;

import com.company.marketer.domain.Client;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ClientRepository extends ReactiveCassandraRepository<Client, Integer> {
    @AllowFiltering
    Flux<Client> findByEmail(String email);
}
