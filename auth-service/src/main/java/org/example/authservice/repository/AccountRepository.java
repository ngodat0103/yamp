package org.example.authservice.repository;
import jakarta.transaction.Transactional;
import org.example.authservice.entity.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends CrudRepository<Account, UUID> {
    @Transactional
    Optional<Account> findByUsername(String username);
    Account findByAccountUuid(UUID accountUuid);
    boolean existsByUsername(String username);
}
