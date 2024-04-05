package it.unimib.tin.TIN.repository;

import it.unimib.tin.TIN.model.CartaDiCredito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartaDiCreditoRepository extends JpaRepository<CartaDiCredito, String> {

    Optional<CartaDiCredito> findById(Long id);

    Optional<CartaDiCredito> findByNumero(String numero);
}
