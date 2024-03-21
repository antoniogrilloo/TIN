package it.unimib.tin.TIN.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class UtenteAutenticato {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    private String nome;

    private String cognome;

    private Date nascita;

    private String indirizzo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cc_id")
    private CartaDiCredito cc;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account account;

    public UtenteAutenticato(Long id, String nome, String cognome, Date nascita, String indirizzo, CartaDiCredito cc,
                             Account account) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.nascita = nascita;
        this.indirizzo = indirizzo;
        this.cc = cc;
        this.account = account;
    }

    public UtenteAutenticato() {

    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public Date getNascita() {
        return nascita;
    }

    public void setNascita(Date nascita) {
        this.nascita = nascita;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public CartaDiCredito getCc() {
        return cc;
    }

    public void setCc(CartaDiCredito cc) {
        this.cc = cc;
    }
}
