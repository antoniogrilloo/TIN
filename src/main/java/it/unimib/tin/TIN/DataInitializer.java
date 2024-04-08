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
            CartaDiCredito c = new CartaDiCredito("1234123412341234", "123", "Admin");
            ua.setAccount(a);
            ua.setCc(c);
            c.setUtente(ua);
            a.setUser(ua);
            arepo.save(a);
            uarepo.save(ua);
            cartaDiCreditoRepository.save(c);
        }

    }
}
