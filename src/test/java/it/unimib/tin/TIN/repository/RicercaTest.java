package it.unimib.tin.TIN.repository;

import it.unimib.tin.TIN.model.Categoria;
import it.unimib.tin.TIN.model.Prodotto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.Silent.class)
public class RicercaTest {

    @Mock
    private ProdottoRepository prepo;

    @Mock
    private CategoriaRepository crepo;

    @Test
    public void testFindByCategory_WithValidCategory_ReturnsMatchingProducts() {
        Prodotto product1 = new Prodotto("Prodotto1", "Descrizione", 1.5);
        Prodotto product2 = new Prodotto("Prodotto2", "Descrizione", 1.5);
        String nomeCategoria = "ELETTRONICA";
        Optional<Categoria> opt = crepo.findByNome(nomeCategoria);
        Categoria c = opt.orElseGet(() -> new Categoria(nomeCategoria));
        product2.setCategoria(c);
        product1.setCategoria(c);
        List<Prodotto> mockProducts = Arrays.asList(product1, product2);

        // Mocking the repository behavior
        when(prepo.findByCategoria(c)).thenReturn(Optional.of(mockProducts));

        // Call the repository method
        List<Prodotto> result = prepo.findByCategoria(c).orElseGet(ArrayList::new);

        // Verify the result
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getName()).isEqualTo("Prodotto1");
        assertThat(result.get(1).getName()).isEqualTo("Prodotto2");
    }

    @Test
    public void testFindByCategory_WithInvalidCategory_ReturnsEmptyList() {
        String nomeCategoria = "NONESISTENTE";
        Optional<Categoria> opt = crepo.findByNome(nomeCategoria);
        Categoria c = opt.orElseGet(() -> new Categoria(nomeCategoria));
        when(prepo.findByCategoria(c)).thenReturn(Optional.of(Arrays.asList()));

        // Call the repository method with an invalid category
        List<Prodotto> result = prepo.findByCategoria(c).orElseGet(ArrayList::new);

        // Verify the result
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
    }

    @Test
    public void testFindByCategory_WithNullCategory_ReturnsEmptyList() {
        // Mocking the repository behavior to return an empty list for null category
        when(prepo.findByCategoria(null)).thenReturn(Optional.of(Arrays.asList()));

        // Call the repository method with null category
        List<Prodotto> result = prepo.findByCategoria(null).orElseGet(ArrayList::new);

        // Verify the result
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
    }

    @Test
    public void testFindByKeyword_WithValidKeyword_ReturnsMatchingProducts() {
        Prodotto product1 = new Prodotto("Prodotto1", "Descrizione del prodotto 1", 1.5);
        Prodotto product2 = new Prodotto("Prodotto2", "Descrizione del prodotto 2", 1.5);
        String keyword = "prodotto";
        List<Prodotto> mockProducts = Arrays.asList(product1, product2);

        // Mocking the repository behavior
        when(prepo.findByKeyword(keyword)).thenReturn(mockProducts);

        // Call the repository method
        List<Prodotto> result = prepo.findByKeyword(keyword);

        // Verify the result
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getName()).isEqualTo("Prodotto1");
        assertThat(result.get(1).getName()).isEqualTo("Prodotto2");
    }

    @Test
    public void testFindByKeywordAndCategory_WithValidInputs_ReturnsMatchingProducts() {
        Prodotto product1 = new Prodotto("Prodotto1", "Descrizione del prodotto 1", 1.5);
        Prodotto product2 = new Prodotto("Prodotto2", "Descrizione del prodotto 2", 1.5);
        String keyword = "prodotto";
        String nomeCategoria = "ELETTRONICA";
        Categoria categoria = new Categoria(nomeCategoria);

        product1.setCategoria(categoria);
        product2.setCategoria(categoria);

        List<Prodotto> mockProducts = Arrays.asList(product1, product2);

        // Mocking the repository behavior
        when(prepo.findByKeywordAndCategoria(keyword, categoria.getNome())).thenReturn(mockProducts);
        when(crepo.findByNome(nomeCategoria)).thenReturn(Optional.of(categoria));

        // Call the repository method
        List<Prodotto> result = prepo.findByKeywordAndCategoria(keyword, nomeCategoria);

        // Verify the result
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getName()).isEqualTo("Prodotto1");
        assertThat(result.get(1).getName()).isEqualTo("Prodotto2");
    }
}
