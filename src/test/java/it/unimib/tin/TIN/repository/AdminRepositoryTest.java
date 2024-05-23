package it.unimib.tin.TIN.repository;

import it.unimib.tin.TIN.model.Categoria;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.Silent.class)
public class AdminRepositoryTest {

    @Mock
    private CategoriaRepository crepo;

    @Test
    public void aggiuntaCategoriaNuova() {
        Categoria c = new Categoria("Nuova Categoria".toUpperCase());
        Long id = 100L;
        c.setId(id);
        when(crepo.save(c)).thenReturn(c);
        Categoria c2 = crepo.save(c);
        assertNotNull(c2);
        assertEquals(id, c2.getId());
        assertEquals("Nuova Categoria".toUpperCase(), c2.getNome());
    }

}
