package org.example.addresssvc.persistence.repository;

import org.example.addresssvc.persistence.document.Address;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;
public interface AddressRepository extends ReactiveCrudRepository<Address, UUID> {
    Flux<Address> findAddressByCustomerUuid(UUID customerUuid);
    Mono<Address> findAddressByName(String name);
    Mono<Address> findAddressByUuid(UUID uuid);
    Mono<Boolean> existsAddressByName(String name);
}
