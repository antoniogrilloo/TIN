package it.unimib.tin.TIN.controller;

import it.unimib.tin.TIN.model.Prodotto;
import it.unimib.tin.TIN.model.UtenteAutenticato;
import it.unimib.tin.TIN.repository.UtenteAutenticatoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class VisualizzaProfiloController {

    private UtenteAutenticatoRepository utenteAutenticatoRepository;

    public VisualizzaProfiloController(UtenteAutenticatoRepository utenteAutenticatoRepository) {
        this.utenteAutenticatoRepository = utenteAutenticatoRepository;
    }

    @GetMapping("/user/{user_id}")
    public ModelAndView infoUtente(@PathVariable("user_id") Long idUser) {
        ModelAndView maw = new ModelAndView();
        Optional<UtenteAutenticato> user = this.utenteAutenticatoRepository.findById(idUser);
        if(user.isPresent()){
            maw = buildInfoUserPage(user.get(), false);
        }
        return maw;
    }

    public ModelAndView buildInfoUserPage(UtenteAutenticato user, Boolean confirm) {
        ModelAndView maw = new ModelAndView();
        maw.addObject("user", user);
        maw.addObject("confirm", confirm);
        maw.setViewName("userInfo");
        return maw;
    }
}
