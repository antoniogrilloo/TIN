package it.unimib.tin.TIN.model;
import jakarta.persistence.*;

@Entity
public class Immagine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "image_id")
    private Long id;

    private String url;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "prodotto_id")
    private Prodotto prodotto;

    public Immagine() { }

    public Immagine(Long id, String url) {
        this.id = id;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
