package it.unimib.tin.TIN.repository;

import it.unimib.tin.TIN.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Optional<Categoria> findById(Long id);

    Optional<Categoria> findByNome(String nome);

}

