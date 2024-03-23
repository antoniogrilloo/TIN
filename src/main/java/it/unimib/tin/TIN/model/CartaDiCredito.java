package it.unimib.tin.TIN.model;

import it.unimib.tin.TIN.exception.CartaDiCreditoException;
import jakarta.persistence.*;

@Entity
public class CartaDiCredito {

    @Id
    private String numero;

    private String cvv;

    private String nomeProprietario;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "u_id")
    private UtenteAutenticato utente;

    public CartaDiCredito(String numero, String cvv, String nomeProprietario) throws CartaDiCreditoException {
        this.setNumero(numero);
        this.setCvv(cvv);
        this.nomeProprietario = nomeProprietario;
    }

    public CartaDiCredito() {

    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) throws CartaDiCreditoException {
        if (numero.length() != 16 || !numero.matches("[0-9]+"))
            throw new CartaDiCreditoException();
        this.numero = numero;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) throws CartaDiCreditoException {
        if (cvv.length() != 3 || !cvv.matches("[0-9]+"))
            throw new CartaDiCreditoException();
        this.cvv = cvv;
    }

    public String getNomeProprietario() {
        return nomeProprietario;
    }

    public void setNomeProprietario(String nomeProprietario) {
        this.nomeProprietario = nomeProprietario;
    }

    public UtenteAutenticato getUtente() {
        return utente;
    }

    public void setUtente(UtenteAutenticato utente) {
        this.utente = utente;
    }
}
