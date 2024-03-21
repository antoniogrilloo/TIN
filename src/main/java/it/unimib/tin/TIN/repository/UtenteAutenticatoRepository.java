package it.unimib.tin.TIN.repository;

import it.unimib.tin.TIN.model.UtenteAutenticato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtenteAutenticatoRepository extends JpaRepository<UtenteAutenticato, Long> {
    Optional<UtenteAutenticato> findById(Long id);
}
