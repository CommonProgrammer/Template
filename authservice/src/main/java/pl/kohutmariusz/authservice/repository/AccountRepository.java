package pl.kohutmariusz.authservice.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import pl.kohutmariusz.authservice.domain.Account;

@Repository
public interface AccountRepository extends org.springframework.data.repository.Repository<Account, String> {

    Optional<Account> findByUsername(String username);

    Account save(Account account);

    void deleteAccountById(String id);
}
