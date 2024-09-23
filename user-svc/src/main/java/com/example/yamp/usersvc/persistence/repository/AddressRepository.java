package com.example.yamp.usersvc.persistence.repository;

import com.example.yamp.usersvc.persistence.entity.Address;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
  Set<Address> findAddressByCustomerUuid(UUID customerUuid);

  Optional<Address> findAddressByCustomerUuidAndName(UUID customerUuid, String name);

  void deleteByUuid(UUID addressUuid);
}
