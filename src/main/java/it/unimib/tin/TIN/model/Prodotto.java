package it.unimib.tin.TIN.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Prodotto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "prodotto_id")
    private Long id;

    private String name;

    private String description;

    private Double price;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "prodotto", cascade = CascadeType.ALL)
    private List<Immagine> immagineList;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UtenteAutenticato venditore;

    @ManyToMany
    List<UtenteAutenticato> preferitiDa;

    public Prodotto() { }

    public Prodotto(Long id, String name, String description, Double price) {
        this.id =id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public UtenteAutenticato getVenditore() {
        return venditore;
    }

    public void setVenditore(UtenteAutenticato venditore) {
        this.venditore = venditore;
    }
}

