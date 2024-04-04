package it.unimib.tin.TIN.controller;

import it.unimib.tin.TIN.model.*;
import it.unimib.tin.TIN.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
public class ProdottiAggiuntiController {
    private AccountRepository accountRepository;
    private ProdottoRepository prodottoRepository;
    private ImmagineRepository immagineRepository;
    private CategoriaRepository categoriaRepository;


    public ProdottiAggiuntiController(ProdottoRepository prodottoRepository, ImmagineRepository immagineRepository, AccountRepository accountRepository, CategoriaRepository categoriaRepository) {
        this.accountRepository = accountRepository;
        this.prodottoRepository = prodottoRepository;
        this.immagineRepository = immagineRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping("/protected/aggiungiProdotto")
    public ModelAndView nuovoProdotto() {
        ModelAndView m = new ModelAndView();
        m.setViewName("aggiungiProdotto");
        return m;
    }

    @GetMapping("/success")
    public ModelAndView successo() {
        ModelAndView m = new ModelAndView();
        m.setViewName("success");
        return m;
    }

    @PostMapping("/protected/aggiungiProdotto")
    public RedirectView nuovoProdottoAggiunto(@RequestParam("img1") MultipartFile img1, @RequestParam("img2") MultipartFile img2, @RequestParam("img3") MultipartFile img3, Prodotto prodotto,  @RequestParam("categoria1") String categoria) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<Account> a = accountRepository.findByUsername(username);
        if(a.isPresent()) {
            UtenteAutenticato ua = a.get().getUser();
            prodotto.setVenditore(ua);
            ua.getProdottiList().add(prodotto);
        }
        Optional<Categoria> ca = categoriaRepository.findByNome(categoria);
        ca.ifPresent(prodotto::setCategoria);
        prodottoRepository.save(prodotto);

        if(img1 != null && !img1.isEmpty()){
            saveImage(img1, prodotto);
        }
        if(img2 != null && !img2.isEmpty()){
            saveImage(img2, prodotto);
        }
        if(img3 != null && !img3.isEmpty()){
            saveImage(img3, prodotto);
        }
        return new RedirectView("/success");
    }



    public void saveImage(MultipartFile img, Prodotto prodotto){
        Immagine im = new Immagine();
        im.setUrl(prodotto.getId().toString() + img.getOriginalFilename());
        if(uploadFile(img, im.getUrl())){
            im.setProdotto(prodotto);
            immagineRepository.save(im);
            prodotto.getImmagineList().add(im);
        }
    }

    public boolean uploadFile(MultipartFile uploadfile, String filename) {
        if(new File("./src/main/resources/static/images/" + filename ).exists())
            return false;
        try {
            String directory = "./src/main/resources/static/images/";
            String filepath = Paths.get(directory, filename).toString();
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filepath)));
            stream.write(uploadfile.getBytes());
            stream.close();
            File file = new File(filepath);
            BufferedImage originalImage = ImageIO.read(file);
            int maxWidth = 900;
            int maxHeight = 900;
            int originalH = originalImage.getHeight();
            int originalW = originalImage.getWidth();
            int height, width;

            if(originalH > maxHeight || originalW > maxWidth) {
                if(originalH >= originalW) {
                    height = maxHeight;
                    // maxHeight : originalH = width : originalW
                    width = (height * originalW) / originalH;
                } else {
                    width = maxWidth;
                    // height : originalH = width : originalW
                    height = (originalH * width) / originalW;
                }

                int typeImage = originalImage.getType();

                BufferedImage resizedImage = new BufferedImage(width, height, typeImage);

                Graphics2D g = resizedImage.createGraphics();
                g.drawImage(originalImage, 0, 0, width, height, null);
                g.dispose();

                String ext = (getExt(filename).isPresent()) ? getExt(filename).get() : "";

                ImageIO.write(resizedImage, ext, file);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public Optional<String> getExt(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

}

