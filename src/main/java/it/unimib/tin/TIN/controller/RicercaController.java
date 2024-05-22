package it.unimib.tin.TIN.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "RicercaController", description = "Operazioni per la ricerca di oggetti in vendita nel sistema.")
public class RicercaController {

    private CategoriaRepository crepo;

    private ProdottoRepository prepo;

    public RicercaController(CategoriaRepository c, ProdottoRepository p) {
        crepo = c;
        prepo = p;
    }

    @Operation(summary = "Visualizza una pagina di ricerca dei prodotti per categoria. Se specificata la categoria come _request param_, nella pagina saranno visualizzati solo i prodotti di quella determinata categoria. Nel caso in cui la categoria non sia valida o non sia stata specificata, il comportamento di default sarà quello di mostrare tutti i prodotti disponibili nel sistema.")
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
            prod = prepo.findAll();
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
