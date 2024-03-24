package it.unimib.tin.TIN.repository;

import it.unimib.tin.TIN.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findById(Long id);

    Optional<Account> findByUsername(String username);

    Optional<Account> findByEmail(String email);
}
