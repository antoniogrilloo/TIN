package it.unimib.tin.TIN;
import it.unimib.tin.TIN.model.Account;
import it.unimib.tin.TIN.model.Categoria;
import it.unimib.tin.TIN.model.UtenteAutenticato;
import it.unimib.tin.TIN.repository.AccountRepository;
import it.unimib.tin.TIN.repository.CategoriaRepository;
import it.unimib.tin.TIN.repository.UtenteAutenticatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CategoriaRepository carepo;

    @Autowired
    private AccountRepository arepo;

    @Autowired
    private UtenteAutenticatoRepository uarepo;
    @Override
    public void run(String... args) throws Exception {

        carepo.deleteAll();

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
        arepo.deleteAll();
        uarepo.deleteAll();
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
