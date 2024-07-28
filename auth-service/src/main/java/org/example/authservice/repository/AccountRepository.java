package org.example.authservice.repository;
import org.example.authservice.entity.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends CrudRepository<Account, UUID> {

    Account save(Account account);
    Optional<Account> findByUsername(String username);
    Account findByAccountUuid(UUID accountUuid);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    void deleteByUsername(String username);

}
