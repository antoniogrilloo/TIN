package it.unimib.tin.TIN.model;

import it.unimib.tin.TIN.exception.AccountException;
import jakarta.persistence.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_id")
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "u_id")
    private UtenteAutenticato user;

    public Account() {

    }

    public Account(String username, String email, String password) throws AccountException {
        this.username = username;
        this.setEmail(email);
        this.setPassword(password);
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws AccountException {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches())
            throw new AccountException();
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        digest.update(password.getBytes());
        byte[] hashedBytes = digest.digest();
        StringBuilder sb = new StringBuilder();
        for(byte b : hashedBytes) {
            sb.append(String.format("%02x", b));
        }
        password = sb.toString();
        this.password = password;
    }

    public UtenteAutenticato getUser() {
        return user;
    }

    public void setUser(UtenteAutenticato user) {
        this.user = user;
    }
}
