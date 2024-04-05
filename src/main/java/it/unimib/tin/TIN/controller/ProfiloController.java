package it.unimib.tin.TIN.controller;

import it.unimib.tin.TIN.model.Categoria;
import it.unimib.tin.TIN.model.UtenteAutenticato;
import it.unimib.tin.TIN.repository.CategoriaRepository;
import it.unimib.tin.TIN.repository.UtenteAutenticatoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class ProfiloController {

    private UtenteAutenticatoRepository utenteAutenticatoRepository;

    private CategoriaRepository crepo;

    public ProfiloController(UtenteAutenticatoRepository utenteAutenticatoRepository, CategoriaRepository crepo) {
        this.utenteAutenticatoRepository = utenteAutenticatoRepository;
        this.crepo = crepo;
    }

    @GetMapping("/user/{user_id}")
    public ModelAndView infoUtente(@PathVariable("user_id") Long idUser) {
        ModelAndView maw = new ModelAndView();
        Optional<UtenteAutenticato> user = this.utenteAutenticatoRepository.findById(idUser);
        String page;
        if(user.isPresent()){
            page = "userInfo";
            List<Categoria> categories = crepo.findAll();
            maw.addObject("categories", categories);
            maw.addObject("user", user);
            maw.addObject("confirm", false);
        } else {
            page = "error";
        }
        maw.setViewName(page);
        return maw;
    }

}
