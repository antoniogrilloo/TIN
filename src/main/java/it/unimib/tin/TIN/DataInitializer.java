package it.unimib.tin.TIN;
import it.unimib.tin.TIN.model.Account;
import it.unimib.tin.TIN.model.Categoria;
import it.unimib.tin.TIN.model.Prodotto;
import it.unimib.tin.TIN.model.UtenteAutenticato;
import it.unimib.tin.TIN.repository.AccountRepository;
import it.unimib.tin.TIN.repository.CategoriaRepository;
import it.unimib.tin.TIN.repository.ProdottoRepository;
import it.unimib.tin.TIN.repository.UtenteAutenticatoRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CategoriaRepository carepo;

    @Autowired
    private AccountRepository arepo;

    @Autowired
    private UtenteAutenticatoRepository uarepo;

    @Autowired
    private ProdottoRepository prepo;

    @Autowired
    private CategoriaRepository crepo;

    @Autowired
    private EntityManager entityManager;

    @Override
    public void run(String... args) throws Exception {

        if (carepo.count() == 0){
            carepo.save(new Categoria("ELETTRONICA"));
            carepo.save(new Categoria("ABBIGLIAMENTO"));
            carepo.save(new Categoria("CASA"));
            carepo.save(new Categoria("LIBRI"));
            carepo.save(new Categoria("MEDIA"));
            carepo.save(new Categoria("BAMBINI"));
            carepo.save(new Categoria("ANIMALI"));
            carepo.save(new Categoria("HOBBY"));
        }

        if(arepo.count() == 0 && uarepo.count() == 0){
            Account a = new Account("admin", "aaa@aaa.com", "admin");
            UtenteAutenticato ua = new UtenteAutenticato("admin", "admin", new Date(), "Via Admin");
            ua.setAccount(a);
            a.setUser(ua);
            arepo.save(a);
            uarepo.save(ua);




        }



    }
}
