package org.example.addresssvc.persistence.repository;

import org.example.addresssvc.persistence.document.Address;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import java.util.UUID;
public interface AddressRepository extends ReactiveCrudRepository<Address, UUID> {
    Flux<Address> findAddressByCustomerUuid(UUID customerUuid);
}
