package it.unimib.tin.TIN.repository;

import it.unimib.tin.TIN.model.Categoria;
import it.unimib.tin.TIN.model.Prodotto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdottoRepository extends JpaRepository<Prodotto, Long> {

    Optional<Prodotto> findById(Long id);

    Optional<Prodotto> findByName(String name);

    Optional<List<Prodotto>> findByCategoria(Categoria categoria);

}
