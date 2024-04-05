package it.unimib.tin.TIN.repository;

import it.unimib.tin.TIN.exception.CartaDiCreditoException;
import it.unimib.tin.TIN.model.UtenteAutenticato;
import it.unimib.tin.TIN.model.CartaDiCredito;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
@SpringBootTest
@RunWith(MockitoJUnitRunner.Silent.class)
public class ModificaProfiloTest {

    @Mock
    private UtenteAutenticatoRepository utenteAutenticatoRepository;

    @Mock
    private CartaDiCreditoRepository cartaDiCreditoRepository;

    @Test
    public void testModifyBiographicData() {
        // Simulazione della modifica dei dati biografici
        String newName = "NuovoNome";
        String newSurname = "NuovoCognome";
        String newAddress = "NuovoIndirizzo";
        Date newBirthDate = new Date();

        // Simula il comportamento della repository
        UtenteAutenticato utenteAutenticato = new UtenteAutenticato();
        when(utenteAutenticatoRepository.findById(1L)).thenReturn(Optional.of(utenteAutenticato));

        // Esegui la modifica dei dati biografici
        utenteAutenticato.setNome(newName);
        utenteAutenticato.setCognome(newSurname);
        utenteAutenticato.setIndirizzo(newAddress);
        utenteAutenticato.setNascita(newBirthDate);

        // Verifica se le modifiche sono state salvate correttamente
        assertTrue(utenteAutenticatoRepository.findById(1L).isPresent());
        assertEquals(newName, utenteAutenticato.getNome());
        assertEquals(newSurname, utenteAutenticato.getCognome());
        assertEquals(newAddress, utenteAutenticato.getIndirizzo());
        assertEquals(newBirthDate, utenteAutenticato.getNascita());
    }

    @Test
    public void testModifyPaymentData() throws CartaDiCreditoException {
        // Simulazione della modifica dei dati di pagamento
        String newCreditCardNumber = "0000000000000000";
        String newCvv = "000";
        String newOwnerName = "NuovoNomeProprietario";

        // Simula il comportamento della repository
        CartaDiCredito cartaDiCredito = new CartaDiCredito();
        when(cartaDiCreditoRepository.findById(1L)).thenReturn(Optional.of(cartaDiCredito));

        // Esegui la modifica dei dati di pagamento
        cartaDiCredito.setNumero(newCreditCardNumber);
        cartaDiCredito.setCvv(newCvv);
        cartaDiCredito.setNomeProprietario(newOwnerName);

        // Verifica se le modifiche sono state salvate correttamente
        assertTrue(cartaDiCreditoRepository.findById(1L).isPresent());
        assertEquals(newCreditCardNumber, cartaDiCredito.getNumero());
        assertEquals(newCvv, cartaDiCredito.getCvv());
        assertEquals(newOwnerName, cartaDiCredito.getNomeProprietario());
    }
}

