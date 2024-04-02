package it.unimib.tin.TIN.controller;

import it.unimib.tin.TIN.model.Prodotto;
import it.unimib.tin.TIN.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class VisualizzaProdottoController {
    private ProdottoRepository prodottoRepository;

    public VisualizzaProdottoController(ProdottoRepository prodottoRepository) {
        this.prodottoRepository = prodottoRepository;
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
}
