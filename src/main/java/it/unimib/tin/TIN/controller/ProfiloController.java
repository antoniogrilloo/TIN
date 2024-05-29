package it.unimib.tin.TIN.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.unimib.tin.TIN.exception.CartaDiCreditoException;
import it.unimib.tin.TIN.model.Account;
import it.unimib.tin.TIN.model.CartaDiCredito;
import it.unimib.tin.TIN.model.Categoria;
import it.unimib.tin.TIN.model.UtenteAutenticato;
import it.unimib.tin.TIN.repository.AccountRepository;
import it.unimib.tin.TIN.repository.CartaDiCreditoRepository;
import it.unimib.tin.TIN.repository.CategoriaRepository;
import it.unimib.tin.TIN.repository.UtenteAutenticatoRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

@Controller
@Tag(name = "ProfiloController", description = "Operazioni basiche per la gestione del profilo di un utente")
public class ProfiloController {

    private UtenteAutenticatoRepository utenteAutenticatoRepository;

    private CategoriaRepository crepo;

    private CartaDiCreditoRepository cartaDiCreditoRepository;

    private AccountRepository arepo;

    public ProfiloController(UtenteAutenticatoRepository utenteAutenticatoRepository, CategoriaRepository crepo, AccountRepository arepo, CartaDiCreditoRepository cartaDiCreditoRepository) {
        this.arepo = arepo;
        this.utenteAutenticatoRepository = utenteAutenticatoRepository;
        this.crepo = crepo;
        this.cartaDiCreditoRepository = cartaDiCreditoRepository;
    }

    @Operation(summary = "Ritorna la pagina che mostra i dettagli del profilo selezionato.")
    @GetMapping("/user/{user_id}")
    public ModelAndView infoUtente(@PathVariable("user_id") Long idUser) {
        ModelAndView maw = new ModelAndView();
        Optional<UtenteAutenticato> user = this.utenteAutenticatoRepository.findById(idUser);
        String page;
        if(user.isPresent()){
            page = "userInfo";
            List<Categoria> categories = crepo.findAll();
            maw.addObject("categories", categories);
            maw.addObject("user", user.get());
            maw.addObject("confirm", false);
        } else {
            page = "error";
        }
        maw.setViewName(page);
        return maw;
    }

    @Operation(summary = "Ritorna la pagina di modifica del profilo o di errore nel caso non se ne hanno le autorizzazioni.")
    @GetMapping("/protected/user/modificaProfilo")
    public ModelAndView paginaModifica() {
        ModelAndView maw = new ModelAndView("modificaProfilo");
        List<Categoria> categories = crepo.findAll();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Account> opt = this.arepo.findByUsername(authentication.getName());
        UtenteAutenticato user;
        if(opt.isEmpty()) {
            maw.setViewName("error");
            return maw;
        }
        maw.addObject("categories", categories);
        user = opt.get().getUser();
        maw.addObject("user", user);
        maw.addObject("account", opt.get());
        maw.addObject("cc", user.getCc());
        return maw;
    }

    @Operation(summary = "Aggiornamento dei dati dell'utente.")
    @PostMapping("/protected/user/aggiornaProfilo")
    public RedirectView aggiornaProfilo(@ModelAttribute UtenteAutenticato user, @ModelAttribute Account a, @ModelAttribute CartaDiCredito c) {
        Optional<UtenteAutenticato> opt = utenteAutenticatoRepository.findById(user.getId());
        UtenteAutenticato uorigin = opt.orElse(null);
        try {
            uorigin.updateInfo(user, c);
        } catch (NullPointerException | CartaDiCreditoException e) {
            return new RedirectView("/error");
        }
        utenteAutenticatoRepository.save(uorigin);
        return new RedirectView("/success");
    }

    @Operation(summary = "Visualizza il form per il cambiamento della password.")
    @GetMapping("/protected/user/cambiaPassword")
    public ModelAndView cambiaPassword() {
        return new ModelAndView("cambiaPassword");
    }

    @Operation(summary = "Metodo che modifica la password nel database.")
    @PostMapping("/protected/user/cambiaPassword/modifica")
    public ModelAndView modificaPassword(@RequestParam String old, @RequestParam String nuova, @RequestParam String nuova_check) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<Account> a = arepo.findByUsername(username);
        if(a.isEmpty())
            return new ModelAndView("error");
        Account account = a.get();
        if(!account.checkPassword(old))
            return new ModelAndView("error");
        if(!nuova.equals(nuova_check))
            return new ModelAndView("error");
        account.setPassword(nuova);
        arepo.save(account);
        return new ModelAndView("success");
    }
}
