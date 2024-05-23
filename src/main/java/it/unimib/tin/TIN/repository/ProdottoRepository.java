package it.unimib.tin.TIN.repository;

import it.unimib.tin.TIN.model.Categoria;
import it.unimib.tin.TIN.model.Prodotto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdottoRepository extends JpaRepository<Prodotto, Long> {

    Optional<Prodotto> findById(Long id);

    Optional<Prodotto> findByName(String name);

    Optional<List<Prodotto>> findByCategoria(Categoria categoria);

    @Query("SELECT p FROM Prodotto p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Prodotto> findByKeyword(@Param("keyword") String keyword);

    @Query("SELECT p FROM Prodotto p WHERE ((LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND (UPPER(p.categoria.nome) " +
            "LIKE UPPER(CONCAT('%', :categoria, '%'))))")
    List<Prodotto> findByKeywordAndCategoria(@Param("keyword") String keyword, @Param("categoria") String categoria);

}
