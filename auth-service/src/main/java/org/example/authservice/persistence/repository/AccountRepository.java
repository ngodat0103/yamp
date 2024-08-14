package org.example.authservice.persistence.repository;
import jakarta.transaction.Transactional;
import org.example.authservice.persistence.entity.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends CrudRepository<Account, UUID> {
    Optional<Account> findByUsername(String username);
    Optional<Account> findByAccountUuid(UUID accountUuid);
    Optional<Account> findByUsernameOrEmail(String username, String email);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByAccountUuid(UUID accountUuid);
}
