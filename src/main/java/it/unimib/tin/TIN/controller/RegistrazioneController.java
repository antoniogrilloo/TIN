package it.unimib.tin.TIN.controller;

import it.unimib.tin.TIN.exception.AccountException;
import it.unimib.tin.TIN.exception.CartaDiCreditoException;
import it.unimib.tin.TIN.model.Account;
import it.unimib.tin.TIN.model.CartaDiCredito;
import it.unimib.tin.TIN.model.RegistraUtenteForm;
import it.unimib.tin.TIN.model.UtenteAutenticato;
import it.unimib.tin.TIN.repository.AccountRepository;
import it.unimib.tin.TIN.repository.CartaDiCreditoRepository;
import it.unimib.tin.TIN.repository.UtenteAutenticatoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class RegistrazioneController {

    private AccountRepository arepo;
    private UtenteAutenticatoRepository urepo;
    private CartaDiCreditoRepository ccrepo;

    public RegistrazioneController(AccountRepository arepo, UtenteAutenticatoRepository urepo, CartaDiCreditoRepository ccrepo) {
        this.arepo = arepo;
        this.urepo = urepo;
        this.ccrepo = ccrepo;
    }

    @RequestMapping("/")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        return mv;
    }

    @GetMapping("/registrazione")
    public ModelAndView registrazione() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("registrazione");
        return mv;
    }

    @PostMapping("/registrazione")
    public RedirectView registra(@ModelAttribute RegistraUtenteForm form){
        ModelAndView mv = new ModelAndView();
        UtenteAutenticato u = null;
        try {
            u = form.getUtente();
            urepo.save(u);
        } catch (CartaDiCreditoException | AccountException e) {
            return new RedirectView("/error");
        }
        return new RedirectView("/confirm");
    }

    @GetMapping("/error")
    public ModelAndView error() {
        return new ModelAndView("error");
    }

    @GetMapping("/confirm")
    public ModelAndView confirmRegistration() {
        return new ModelAndView("registrazioneAvvenuta");
    }

    @GetMapping("/protected")
    public ModelAndView homepage() {
        return new ModelAndView("homepage");
    }

}
