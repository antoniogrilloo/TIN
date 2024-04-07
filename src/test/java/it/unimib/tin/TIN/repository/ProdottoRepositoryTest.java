package it.unimib.tin.TIN.repository;

import it.unimib.tin.TIN.model.Categoria;
import it.unimib.tin.TIN.model.Immagine;
import it.unimib.tin.TIN.model.Prodotto;

import it.unimib.tin.TIN.model.UtenteAutenticato;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.text.html.Option;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.Silent.class)
public class ProdottoRepositoryTest {

    @Mock
    private ProdottoRepository prepo;
    @Mock
    private ImmagineRepository irepo;
    @Mock
    private CategoriaRepository crepo;
    @Mock
    private UtenteAutenticatoRepository urepo;

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
    public void testAssegnaImmagini() {
        Prodotto existingProd = new Prodotto("Samsung S23", "Telefono usato poco, di ottima qualità", 800.0);
        Immagine existingIm = new Immagine("immagine.jpg");
        Long prodId = 1L;
        Long imId = 2L;
        existingProd.setId(prodId);
        existingIm.setId(imId);
        existingIm.setProdotto(existingProd);
        when(prepo.findById(prodId)).thenReturn(Optional.of(existingProd));
        when(irepo.findById(imId)).thenReturn(Optional.of(existingIm));

        assertEquals(existingIm.getProdotto(), existingProd);
        assertEquals("immagine.jpg", existingIm.getUrl());
    }


    @Test
    public void testDelete() {
        long prodId = 1L;
        doNothing().when(prepo).deleteById(prodId);
        prepo.deleteById(prodId);
        verify(prepo, times(1)).deleteById(prodId);
    }

    @Test
    public void testVisualizzazioneInfoProdotto(){
        Prodotto p = new Prodotto("Samsung S23", "Telefono usato poco, di ottima qualità", 800.0);
        Optional<Categoria> c = crepo.findByNome("ELETTRONICA");
        Categoria cate = null;
        if(c.isPresent()){
            cate = c.get();
        }
        p.setCategoria(cate);
        Long prodId = 1L;
        p.setId(prodId);
        when(prepo.findById(anyLong())).thenReturn(Optional.of(p));
        Optional<Prodotto> result = prepo.findById(prodId);

        assertTrue(result.isPresent());
        assertEquals(p, result.get());
        assertEquals(p.getName(), result.get().getName());
        assertEquals(p.getDescription(), result.get().getDescription());
        assertEquals(p.getPrice(), result.get().getPrice(), 0);
        assertEquals(p.getCategoria(), result.get().getCategoria());
    }

    @Test
    public void testDeleteProductForUser() {
        UtenteAutenticato ua = new UtenteAutenticato("utente", "utente", new Date(), "via utente");
        Prodotto p = new Prodotto("Prodotto", "Descrizione prodotto", 99.0);
        long userId = 1L;
        long productId = 100L;

        ua.setId(userId);
        p.setId(productId);
        p.setVenditore(ua);
        List<Prodotto> pList = new ArrayList<>();
        ua.setProdottiList(pList);
        ua.getProdottiList().add(p);

        when(urepo.findById(userId)).thenReturn(Optional.of(ua));
        when(prepo.findById(productId)).thenReturn(Optional.of(p));

        ua.deleteProdotto(p);

        assertEquals(0, ua.getProdottiList().size());
        assertFalse(ua.getProdottiList().contains(p));
        assertFalse(prepo.existsById(productId));

    }
}
