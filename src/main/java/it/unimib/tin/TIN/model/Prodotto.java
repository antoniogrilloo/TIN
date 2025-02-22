package it.unimib.tin.TIN.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    public Prodotto() {
        this.immagineList = new ArrayList<>();
    }

    public Prodotto(String name, String description, Double price) {
        super();
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<Immagine> getImmagineList() {
        return immagineList;
    }

    public void setImmagineList(List<Immagine> immagineList) {
        this.immagineList = immagineList;
    }

    @Override
    public String toString() {
        return id + " " + name + " " + price + " " + venditore.getAccount().getUsername() + " " + categoria.getNome();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prodotto prodotto = (Prodotto) o;
        return Objects.equals(id, prodotto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

