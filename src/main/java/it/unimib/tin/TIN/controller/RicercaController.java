package it.unimib.tin.TIN.controller;

import it.unimib.tin.TIN.model.Categoria;
import it.unimib.tin.TIN.model.Prodotto;
import it.unimib.tin.TIN.repository.CategoriaRepository;
import it.unimib.tin.TIN.repository.ProdottoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class RicercaController {

    private CategoriaRepository crepo;

    private ProdottoRepository prepo;

    public RicercaController(CategoriaRepository c, ProdottoRepository p) {
        crepo = c;
        prepo = p;
    }

    @GetMapping("/search")
    public ModelAndView visualizzaRisultati(@RequestParam(name = "categoria", required = false) String categoria) {
        ModelAndView maw = new ModelAndView("visualizzaRisultati");
        List<Categoria> categories = crepo.findAll();
        maw.addObject("categories", categories);
        Optional<Categoria> opt;
        Categoria c;
        Optional<List<Prodotto>> p;
        List<Prodotto> prod;
        if (categoria == null || categoria.isEmpty() || categoria.equals("0")){
            prod = new ArrayList<>();
            c = new Categoria("");
            c.setId(-1L);
        } else {
            opt = crepo.findByNome(categoria);
            c = opt.orElseGet(() -> new Categoria(""));
            p = prepo.findByCategoria(c);
            prod = p.orElseGet(ArrayList::new);
        }
        maw.addObject("prodotti", prod);
        maw.addObject("categoria", c);
        return maw;
    }


}
