package it.unimib.tin.TIN.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.unimib.tin.TIN.model.Categoria;
import it.unimib.tin.TIN.repository.CategoriaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@Tag(name = "AdminController", description = "Operazioni che possono essere svolte soltanto da utenti admin.")
public class AdminController {

    private CategoriaRepository crepo;

    public AdminController(CategoriaRepository crepo) {
        this.crepo = crepo;
    }

    @Operation(summary = "Visualizza la pagina di aggiunta delle categorie.")
    @GetMapping("/protected/admin/aggiungiCategoria")
    public ModelAndView aggiungiCategoria() {
        ModelAndView maw = new ModelAndView("nuovaCategoria");
        List<Categoria> categories = crepo.findAll();
        maw.addObject("categories", categories);
        return maw;
    }

    @Operation(summary = "Aggiunge nuove categorie nel sistema; funziona solo se l'utente è un admin.")
    @PostMapping("/protected/admin/inserisciCategoria")
    public RedirectView inserisciCategoria(@RequestParam String categoria) {
        Categoria repo = crepo.findByNome(categoria.toUpperCase()).orElse(null);
        if (repo != null) {
            return new RedirectView("/protected/admin/aggiungiCategoria?error");
        }
        repo = new Categoria(categoria.toUpperCase());
        this.crepo.save(repo);
        return new RedirectView("/protected/admin/aggiungiCategoria?success");
    }
}
