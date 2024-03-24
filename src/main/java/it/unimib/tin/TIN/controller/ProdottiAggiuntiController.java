package it.unimib.tin.TIN.controller;

import it.unimib.tin.TIN.model.Prodotto;
import it.unimib.tin.TIN.model.UtenteAutenticato;
import it.unimib.tin.TIN.repository.ProdottoRepository;
import it.unimib.tin.TIN.repository.UtenteAutenticatoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ProdottiAggiuntiController {

    private UtenteAutenticatoRepository utenteAutenticatoRepository;
    private ProdottoRepository prodottoRepository;


    public ProdottiAggiuntiController(UtenteAutenticatoRepository utenteAutenticatoRepository, ProdottoRepository prodottoRepository) {
        this.utenteAutenticatoRepository = utenteAutenticatoRepository;
        this.prodottoRepository = prodottoRepository;
    }

    @GetMapping("/aggiungiProdotto")
    public ModelAndView nuovoProdotto() {
        ModelAndView m = new ModelAndView();
        m.setViewName("aggiungiProdotto");
        return m;
    }

    @PostMapping("/aggiungiProdotto")
    public RedirectView nuovoProdottoAggiunto(Prodotto prodotto) {
        UtenteAutenticato ua = new UtenteAutenticato();
        prodotto.setVenditore(ua);
        prodottoRepository.save(prodotto);
        return new RedirectView("/success");
    }
}

