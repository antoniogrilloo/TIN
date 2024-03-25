package it.unimib.tin.TIN.repository;

import it.unimib.tin.TIN.model.Immagine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImmagineRepository extends JpaRepository<Immagine, Long> {

    Optional<Immagine> findById(Long id);

}