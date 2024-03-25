package it.unimib.tin.TIN.repository;

import it.unimib.tin.TIN.model.Prodotto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.Silent.class)
public class ProdottoRepositoryTests {

    @Mock
    private ProdottoRepository prepo;

    @Test
    public void testFindById(){
        Prodotto p = new Prodotto("Samsung S23", "Telefono usato poco, di ottima qualità", 800.0);

        Long prodId = 1L;
        p.setId(prodId);
        when(prepo.findById(anyLong())).thenReturn(Optional.of(p));
        Optional<Prodotto> result = prepo.findById(prodId);

        assertTrue(result.isPresent());
        assertEquals(p, result.get());
    }

    @Test
    public void testUpdateDatiProdotto() {
        Prodotto existingProd = new Prodotto("Samsung S23", "Telefono usato poco, di ottima qualità", 800.0);
        Prodotto updatedProd = new Prodotto("Samsung S22", "Telefono usato tanto, di pessima qualità", 80.0);
        Long prodId = 1L;
        existingProd.setId(prodId);
        updatedProd.setId(prodId);
        when(prepo.findById(prodId)).thenReturn(Optional.of(existingProd));
        when(prepo.save(updatedProd)).thenReturn(updatedProd);

        Prodotto result = prepo.save(updatedProd);

        assertNotNull(result);
        assertEquals(prodId, result.getId());
        assertEquals("Samsung S22", result.getName());
        assertEquals(80.0, result.getPrice(), 0);
    }

    @Test
    public void testDelete() {
        long prodId = 1L;
        doNothing().when(prepo).deleteById(prodId);
        prepo.deleteById(prodId);
        verify(prepo, times(1)).deleteById(prodId);
    }
}
