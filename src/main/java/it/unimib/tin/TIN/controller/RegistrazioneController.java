package it.unimib.tin.TIN.controller;

import it.unimib.tin.TIN.exception.AccountException;
import it.unimib.tin.TIN.exception.CartaDiCreditoException;
import it.unimib.tin.TIN.model.Categoria;
import it.unimib.tin.TIN.repository.CategoriaRepository;
import org.springframework.security.core.Authentication;
import it.unimib.tin.TIN.model.RegistraUtenteForm;
import it.unimib.tin.TIN.model.UtenteAutenticato;
import it.unimib.tin.TIN.repository.UtenteAutenticatoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

@Controller
public class RegistrazioneController {

    private UtenteAutenticatoRepository urepo;

    private CategoriaRepository crepo;

    public RegistrazioneController(UtenteAutenticatoRepository urepo, CategoriaRepository crepo) {
        this.urepo = urepo;
        this.crepo = crepo;
    }

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

    @RequestMapping("/login")
    public ModelAndView login() {
        ModelAndView maw = new ModelAndView("index");
        List<Categoria> categories = crepo.findAll();
        maw.addObject("categories", categories);
        return maw;
    }

    @GetMapping("/registrazione")
    public ModelAndView registrazione() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("registrazione");
        return mv;
    }

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
        ModelAndView maw = new ModelAndView("homepage");
        List<Categoria> categories = crepo.findAll();
        maw.addObject("categories", categories);
        return maw;
    }

}
