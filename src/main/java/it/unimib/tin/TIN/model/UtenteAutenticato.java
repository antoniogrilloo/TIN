package it.unimib.tin.TIN.model;

import it.unimib.tin.TIN.exception.CartaDiCreditoException;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
public class UtenteAutenticato {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    private String nome;

    private String cognome;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date nascita;

    private String indirizzo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cc_id")
    private CartaDiCredito cc;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "venditore", cascade = CascadeType.ALL)
    private List<Prodotto> prodottiList;

    public UtenteAutenticato(String nome, String cognome, Date nascita, String indirizzo) {
        this.nome = nome;
        this.cognome = cognome;
        this.nascita = nascita;
        this.indirizzo = indirizzo;
    }

    public UtenteAutenticato() {

    }

    public void setId(Long id) {
        this.id = id;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Prodotto> getProdottiList() {
        return prodottiList;
    }

    public void setProdottiList(List<Prodotto> prodottiList) {
        this.prodottiList = prodottiList;
    }

    public void updateInfo(UtenteAutenticato updatedUser, CartaDiCredito updatedCC) throws CartaDiCreditoException {
        this.setNome(updatedUser.nome);
        this.setCognome(updatedUser.cognome);
        this.setIndirizzo(updatedUser.indirizzo);
        this.setNascita(updatedUser.nascita);
        this.cc.setNomeProprietario(updatedCC.getNomeProprietario());
        this.cc.setCvv(updatedCC.getCvv());
        this.cc.setNumero(updatedCC.getNumero());
    }

    public void deleteProdotto(Prodotto p) {
        p.setVenditore(null);
        p.setCategoria(null);
        this.prodottiList.remove(p);
    }

    @Override
    public String toString() {
        return "UtenteAutenticato{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", nascita=" + nascita +
                ", indirizzo='" + indirizzo + '\'' +
                '}';
    }
}
