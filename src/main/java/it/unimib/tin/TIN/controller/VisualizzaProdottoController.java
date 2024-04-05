package it.unimib.tin.TIN.controller;

import it.unimib.tin.TIN.model.Prodotto;
import it.unimib.tin.TIN.model.UtenteAutenticato;
import it.unimib.tin.TIN.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Controller
public class VisualizzaProdottoController {
    private ProdottoRepository prodottoRepository;

    private UtenteAutenticatoRepository utenteAutenticatoRepository;

    public VisualizzaProdottoController(ProdottoRepository prodottoRepository, UtenteAutenticatoRepository utenteAutenticatoRepository) {
        this.prodottoRepository = prodottoRepository;
        this.utenteAutenticatoRepository = utenteAutenticatoRepository;
    }

    @GetMapping("/prodotto/{idProdotto}")
    public ModelAndView infoProdotto(@PathVariable("idProdotto") Long idProdotto) {
        ModelAndView maw = new ModelAndView();
        Optional<Prodotto> p = this.prodottoRepository.findById(idProdotto);
        if(p.isPresent()){
            maw = buildInfoProdottoPage(p.get(), false);
        }
        return maw;
    }

    public ModelAndView buildInfoProdottoPage(Prodotto p, Boolean confirm) {
        ModelAndView maw = new ModelAndView();
        maw.addObject("prodotto", p);
        maw.addObject("confirm", confirm);
        maw.setViewName("prodottoInfo");
        return maw;
    }

    @PostMapping("/user/eliminaProdotto/{idUser}")
    public RedirectView eliminaProdotto(@PathVariable Long idUser, @RequestParam Long idProdotto) {
        Optional<UtenteAutenticato> utente = utenteAutenticatoRepository.findById(idUser);
        Optional<Prodotto> prodotto = prodottoRepository.findById(idProdotto);
        if(utente.isPresent() && prodotto.isPresent()) {
            utente.get().deleteProdotto(prodotto.get());
            utenteAutenticatoRepository.save(utente.get());
            prodottoRepository.delete(prodotto.get());
        }
        return new RedirectView("/user/" + idUser);
    }
}
