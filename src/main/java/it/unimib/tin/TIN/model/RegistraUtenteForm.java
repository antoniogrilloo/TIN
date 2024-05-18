package it.unimib.tin.TIN.model;

import it.unimib.tin.TIN.exception.AccountException;
import it.unimib.tin.TIN.exception.CartaDiCreditoException;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class RegistraUtenteForm {
    private String nome;
    private String cognome;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date nascita;
    private String indirizzo;
    private String ccn;
    private String cvv;
    private String proprietario;
    private String username;
    private String email;
    private String password;


    public RegistraUtenteForm(String nome, String cognome, Date nascita, String indirizzo, String ccn, String cvv, String proprietario, String email, String username, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.nascita = nascita;
        this.indirizzo = indirizzo;
        this.ccn = ccn;
        this.cvv = cvv;
        this.proprietario = proprietario;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public UtenteAutenticato getUtente() throws CartaDiCreditoException, AccountException {
        UtenteAutenticato u = new UtenteAutenticato(nome, cognome, nascita, indirizzo);
        CartaDiCredito cc = this.getCC();
        Account a = this.getAccount();
        a.setEnabled(false);
        u.setCc(cc);
        cc.setUtente(u);
        u.setAccount(a);
        a.setUser(u);
        return u;
    }

    public CartaDiCredito getCC() throws CartaDiCreditoException {
        return new CartaDiCredito(ccn, cvv, proprietario);
    }
    public Account getAccount() throws AccountException {
        return new Account(username, email, password);
    }
}
