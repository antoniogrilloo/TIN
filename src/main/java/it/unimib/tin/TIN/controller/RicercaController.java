package it.unimib.tin.TIN.controller;

import it.unimib.tin.TIN.model.Categoria;
import it.unimib.tin.TIN.repository.CategoriaRepository;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class RicercaController {

    private CategoriaRepository crepo;

    public RicercaController(CategoriaRepository c) {
        crepo = c;
    }


}
