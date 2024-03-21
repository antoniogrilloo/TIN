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
        u.setCc(cc);
        cc.setUtente(u);
        u.setAccount(a);
        a.setUser(u);
        return u;
    }

    public CartaDiCredito getCC() throws CartaDiCreditoException {
        if (ccn.length() != 16 || cvv.length() != 3)
            throw new CartaDiCreditoException();
        if (!ccn.matches("[0-9]+") || !cvv.matches("[0-9]+"))
            throw new CartaDiCreditoException();
        CartaDiCredito cc = new CartaDiCredito(ccn, cvv, proprietario);
        return cc;
    }
    public Account getAccount() throws AccountException {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches())
            throw new AccountException();
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(password.getBytes());
            byte[] hashedBytes = digest.digest();
            StringBuilder sb = new StringBuilder();
            for(byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            password = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Account a = new Account(username, email, password);
        return a;
    }
}
