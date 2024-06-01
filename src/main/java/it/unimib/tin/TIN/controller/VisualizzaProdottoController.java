package it.unimib.tin.TIN.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.unimib.tin.TIN.model.Categoria;
import it.unimib.tin.TIN.model.Account;
import it.unimib.tin.TIN.model.Immagine;
import it.unimib.tin.TIN.model.Prodotto;
import it.unimib.tin.TIN.model.UtenteAutenticato;
import it.unimib.tin.TIN.repository.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
@Tag(name = "VisualizzaProdottoController", description = "Operazioni per la visualizzazione dei prodotti presenti nel sistema.")
public class VisualizzaProdottoController {
    private ProdottoRepository prodottoRepository;

    private CategoriaRepository crepo;

    private UtenteAutenticatoRepository utenteAutenticatoRepository;

    private AccountRepository accountRepository;

    private ImmagineRepository immagineRepository;

    public VisualizzaProdottoController(ProdottoRepository prodottoRepository, CategoriaRepository c, UtenteAutenticatoRepository utenteAutenticatoRepository, AccountRepository accountRepository, ImmagineRepository immagineRepository) {
        this.prodottoRepository = prodottoRepository;
        this.crepo = c;
        this.utenteAutenticatoRepository = utenteAutenticatoRepository;
        this.accountRepository = accountRepository;
        this.immagineRepository = immagineRepository;
    }

    @Operation(summary = "Ritorna la pagina che mostra i dettagli del prodotto selezionato.")
    @GetMapping("/prodotto/{idProdotto}")
    public ModelAndView infoProdotto(@PathVariable("idProdotto") Long idProdotto) {
        ModelAndView maw = new ModelAndView();
        Optional<Prodotto> p = this.prodottoRepository.findById(idProdotto);
        if(p.isPresent()){
            maw = buildInfoProdottoPage(p.get(), false);
        } else{
            maw.setViewName("error");
        }
        return maw;
    }

    public ModelAndView buildInfoProdottoPage(Prodotto p, Boolean confirm) {
        ModelAndView maw = new ModelAndView();
        List<Categoria> categories = crepo.findAll();
        maw.addObject("categories", categories);
        maw.addObject("prodotto", p);
        maw.addObject("confirm", confirm);
        maw.setViewName("prodottoInfo");
        return maw;
    }

    @Operation(summary = "Effettua l'eliminazione del prodotto e redireziona all'endpoint `/user/{user_id}`.")
    @GetMapping("/protected/eliminaProdotto/{idProdotto}")
    public RedirectView eliminaProdotto(@PathVariable Long idProdotto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Account> account = accountRepository.findByUsername(authentication.getName());
        if (account.isEmpty()){
            return new RedirectView("/error");
        }
        UtenteAutenticato utente = account.get().getUser();
        Optional<Prodotto> prodotto = prodottoRepository.findById(idProdotto);
        if (prodotto.isEmpty()){
            return new RedirectView("/error");
        }

        utente.deleteProdotto(prodotto.get());
        utenteAutenticatoRepository.save(utente);
        Prodotto p = prodotto.get();
        List<Immagine> img = p.getImmagineList();

        for (Immagine elemento : img) {
            immagineRepository.delete(elemento);
            eliminaImmagine("./img/", elemento.getUrl());

        }

        prodottoRepository.delete(p);

        return new RedirectView("/user/" + utente.getId());
    }

    public static void eliminaImmagine(String percorsoCartella, String nomeImmagine) {
        File immagine = new File(percorsoCartella, nomeImmagine);
        if (immagine.exists() && immagine.isFile()) {
            immagine.delete();
        }
    }

    @Operation(summary = "Mostra le immagini associate al prodotto.")
    @GetMapping(value = "/img/{name}")
    public ResponseEntity<Resource> getImg(@PathVariable String name){
        ByteArrayResource inputStream = null;
        try {
            inputStream = new ByteArrayResource(Files.readAllBytes(Paths.get(
                    "./img/" + name
            )));
        } catch (IOException e) {
            return null;
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentLength(inputStream.contentLength())
                .body(inputStream);
    }



}
