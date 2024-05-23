package it.unimib.tin.TIN;
import it.unimib.tin.TIN.model.Account;
import it.unimib.tin.TIN.model.CartaDiCredito;
import it.unimib.tin.TIN.model.Categoria;
import it.unimib.tin.TIN.model.UtenteAutenticato;
import it.unimib.tin.TIN.repository.AccountRepository;
import it.unimib.tin.TIN.repository.CartaDiCreditoRepository;
import it.unimib.tin.TIN.repository.CategoriaRepository;
import it.unimib.tin.TIN.repository.ProdottoRepository;
import it.unimib.tin.TIN.repository.UtenteAutenticatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AccountRepository arepo;

    @Autowired
    private UtenteAutenticatoRepository uarepo;

    @Autowired
    private CartaDiCreditoRepository cartaDiCreditoRepository;

    @Autowired
    private CategoriaRepository crepo;

    @Override
    public void run(String... args) throws Exception {

        if (crepo.count() == 0){
            crepo.save(new Categoria("ELETTRONICA"));
            crepo.save(new Categoria("ABBIGLIAMENTO"));
            crepo.save(new Categoria("CASA"));
            crepo.save(new Categoria("LIBRI"));
            crepo.save(new Categoria("MEDIA"));
            crepo.save(new Categoria("BAMBINI"));
            crepo.save(new Categoria("ANIMALI"));
            crepo.save(new Categoria("HOBBY"));
        }

        if(arepo.count() == 0 && uarepo.count() == 0){
            Account a = new Account("admin", "aaa@aaa.com", "admin");
            UtenteAutenticato ua = new UtenteAutenticato("admin", "admin", new Date(), "Via Admin");
            Account a2 = new Account("antonio", "antonio@tin.it", "antonio");
            UtenteAutenticato ua2 = new UtenteAutenticato("antonio", "grillo", new Date(), "Via Admin");
            a.setEnabled(true);
            a2.setEnabled(true);
            CartaDiCredito c = new CartaDiCredito("1234123412341234", "123", "Admin");
            CartaDiCredito c2 = new CartaDiCredito("2234123412341234", "123", "Admin");
            ua.setAccount(a);
            ua.setCc(c);
            ua2.setAccount(a2);
            ua2.setCc(c2);
            c.setUtente(ua);
            c2.setUtente(ua2);
            a2.setUser(ua2);
            c.setUtente(ua);
            a.setUser(ua);
            arepo.save(a);
            arepo.save(a2);
            uarepo.save(ua);
            uarepo.save(ua2);
            cartaDiCreditoRepository.save(c);
        }

    }
}
