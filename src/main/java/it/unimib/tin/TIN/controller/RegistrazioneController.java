package it.unimib.tin.TIN.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.unimib.tin.TIN.exception.AccountException;
import it.unimib.tin.TIN.exception.CartaDiCreditoException;
import it.unimib.tin.TIN.model.Account;
import it.unimib.tin.TIN.model.Categoria;
import it.unimib.tin.TIN.repository.AccountRepository;
import it.unimib.tin.TIN.repository.CategoriaRepository;
import org.springframework.security.core.Authentication;
import it.unimib.tin.TIN.model.RegistraUtenteForm;
import it.unimib.tin.TIN.model.UtenteAutenticato;
import it.unimib.tin.TIN.repository.UtenteAutenticatoRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

@Controller
@Tag(name = "RegistrazioneController", description = "Operazioni per la registrazione di un utente nel sistema.")
public class RegistrazioneController {

    private UtenteAutenticatoRepository urepo;

    private CategoriaRepository crepo;

    private AccountRepository arepo;

    public RegistrazioneController(UtenteAutenticatoRepository urepo, CategoriaRepository crepo, AccountRepository arepo) {
        this.urepo = urepo;
        this.crepo = crepo;
        this.arepo = arepo;
    }

    @Operation(summary = "Permette di visualizzare la pagina principale dell'applicazione. Se l'utente non è autenticato, verrà reindirizzato alla pagina che presenta il form di login; mentre, se fosse già autenticato, verrà reindirizzato alla pagina personalizzata per l'utente specifico.")
    @RequestMapping("/")
    public RedirectView index(Authentication authentication, @RequestParam(name = "error", required = false) String error,
                              @RequestParam(name = "logout", required = false) String logout) {
        RedirectView mv;
        if (authentication != null && authentication.isAuthenticated()) {
            mv = new RedirectView("/protected");
        } else {
            error = (error != null) ? "error" : "";
            logout = (logout != null) ? "logout" : "";
            String params = "?" + ((!error.isEmpty()) ? error : logout) ;
            mv = new RedirectView("/login" + params);
        }
        return mv;
    }

    @Operation(summary = "Ritorna la pagina di login.")
    @RequestMapping("/login")
    public ModelAndView login() {
        ModelAndView maw = new ModelAndView("index");
        List<Categoria> categories = crepo.findAll();
        maw.addObject("categories", categories);
        return maw;
    }

    @Operation(summary = "Permette di avviare la procedura di registrazione di un utente.")
    @GetMapping("/registrazione")
    public ModelAndView registrazione() {
        ModelAndView mv = new ModelAndView();
        List<Categoria> categories = crepo.findAll();
        mv.addObject("categories", categories);
        mv.setViewName("registrazione");
        return mv;
    }

    @Operation(summary = "Aggiunge l'utente, con corrispettivo account e dati di carta di credito, sul sistema")
    @PostMapping("/registrazione")
    public RedirectView registra(@ModelAttribute RegistraUtenteForm form){
        UtenteAutenticato u;
        try {
            u = form.getUtente();
            urepo.save(u);
        } catch (CartaDiCreditoException | AccountException e) {
            return new RedirectView("/error");
        }
        return new RedirectView("/confirm");
    }

    @Operation(summary = "Visualizza una generica pagina di errore.")
    @GetMapping("/error")
    public ModelAndView error() {
        return new ModelAndView("error");
    }

    @Operation(summary = "Visualizza la pagina di conferma della autenticazione.")
    @GetMapping("/confirm")
    public ModelAndView confirmRegistration() {
        ModelAndView maw = new ModelAndView("registrazioneAvvenuta");
        List<Categoria> categories = crepo.findAll();
        maw.addObject("categories", categories);
        return maw;
    }

    @Operation(summary = "Visualizza la homepage dell'utente. È accessibile solo se si è autenticati.")
    @GetMapping("/protected")
    public ModelAndView homepage() {
        ModelAndView maw = new ModelAndView("homepage");
        List<Categoria> categories = crepo.findAll();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<Account> accountUser = arepo.findByUsername(username);
        Optional<UtenteAutenticato> user = urepo.findById(accountUser.get().getId());
        maw.addObject("user", user);
        maw.addObject("categories", categories);
        return maw;
    }

}
