package it.unimib.tin.TIN.repository;

import it.unimib.tin.TIN.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, String> {

    VerificationToken findByToken(String token);

    void deleteByToken(String token);

}
