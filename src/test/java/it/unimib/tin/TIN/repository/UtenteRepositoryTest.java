package it.unimib.tin.TIN.repository;

import it.unimib.tin.TIN.exception.AccountException;
import it.unimib.tin.TIN.exception.CartaDiCreditoException;
import it.unimib.tin.TIN.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.Silent.class)
public class UtenteRepositoryTest {

    @Mock
    private UtenteAutenticatoRepository urepo;

    @Mock
    private AccountRepository arepo;

    @Test
    public void testFindById() {
        UtenteAutenticato user = new UtenteAutenticato("Pasquale", "Cafiero",
                                                        new Date(),"Via del Campo");
        Long userId = 1L;
        user.setId(userId);
        when(urepo.findById(anyLong())).thenReturn(Optional.of(user));
        Optional<UtenteAutenticato> result = urepo.findById(userId);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    public void testUpdateDatiBiografici() {
        UtenteAutenticato existingUser = new UtenteAutenticato("Pasquale", "Cafiero",
                new Date(),"Via del Campo");
        UtenteAutenticato updatedUser = new UtenteAutenticato("Giovanni", "Palestina",
                new Date(),"Via del Campo");
        Long userId = 1L;
        existingUser.setId(userId);
        updatedUser.setId(userId);
        when(urepo.findById(userId)).thenReturn(Optional.of(existingUser));
        when(urepo.save(updatedUser)).thenReturn(updatedUser);

        UtenteAutenticato result = urepo.save(updatedUser);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("Giovanni", result.getNome());
        assertEquals("Palestina", result.getCognome());
    }

    @Test
    public void testUpdateDatiBancari() throws AccountException {
        UtenteAutenticato u = new UtenteAutenticato("Pasquale", "Cafiero", new Date(), "Via Croce");
        CartaDiCredito cc = null;
        try {
            cc = new CartaDiCredito("1234567890123456", "212", "Pasquale");
        } catch (CartaDiCreditoException e) {
            throw new RuntimeException(e);
        }
        Account a = new Account("pcafiero", "pcaf@mail.com", "qas34esdre");
        Long userId = 1L;
        u.setId(userId);
        u.setCc(cc);
        cc.setUtente(u);
        u.setAccount(a);
        a.setUser(u);

        when(urepo.findById(userId)).thenReturn(Optional.of(u));
        when(urepo.save(u)).thenReturn(u);

        UtenteAutenticato result = urepo.save(u);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("Pasquale", result.getNome());
        assertEquals("Cafiero", result.getCognome());
    }

    @Test
    public void testDelete() {
        long userId = 1L;
        doNothing().when(urepo).deleteById(userId);
        urepo.deleteById(userId);
        verify(urepo, times(1)).deleteById(userId);
    }

    @Test
    public void testSave() throws CartaDiCreditoException, AccountException {
        UtenteAutenticato u = new UtenteAutenticato("Pasquale", "Cafiero", new Date(), "Via Croce");
        CartaDiCredito cc = new CartaDiCredito("1234567890123456", "212", "Pasquale");
        Account a = new Account("pcafiero", "pcaf@mail.com", "qas34esdre");
        Long userId = 1L;
        u.setId(userId);
        u.setCc(cc);
        cc.setUtente(u);
        u.setAccount(a);
        a.setUser(u);
        when(urepo.save(any(UtenteAutenticato.class))).thenAnswer(invocation -> {
            UtenteAutenticato newUser = invocation.getArgument(0);
            newUser.setId(1L);
            return newUser;
        });

        UtenteAutenticato savedUser = urepo.save(u);

        assertNotNull(savedUser.getId());
        assertEquals(Optional.of(1L), Optional.ofNullable(savedUser.getId()));
        assertEquals("Pasquale", savedUser.getNome());
        assertEquals("Cafiero", savedUser.getCognome());
        assertEquals("pcafiero", savedUser.getAccount().getUsername());
        assertEquals("pcaf@mail.com", savedUser.getAccount().getEmail());
        assertEquals("1234567890123456", savedUser.getCc().getNumero());
    }

    @Test
    public void testVisualizzazioneInfoUtente() throws AccountException {
        UtenteAutenticato u = new UtenteAutenticato("Utente", "Autenticato", new Date(),"Via Utente");
        Account a = new Account("pcafiero", "pcaf@mail.com", "qas34esdre");
        Long userId = 1L;
        u.setId(userId);
        u.setAccount(a);

        when(urepo.save(any(UtenteAutenticato.class))).thenAnswer(invocation -> {
            UtenteAutenticato newUser = invocation.getArgument(0);
            newUser.setId(1L);
            return newUser;
        });

        UtenteAutenticato savedUser = urepo.save(u);


        assertNotNull(savedUser.getId());
        assertEquals(Optional.of(1L), Optional.ofNullable(savedUser.getId()));
        assertEquals("Utente", savedUser.getNome());
        assertEquals("Autenticato", savedUser.getCognome());
        assertEquals("pcafiero", savedUser.getAccount().getUsername());
        assertEquals("pcaf@mail.com", savedUser.getAccount().getEmail());
    }
}
