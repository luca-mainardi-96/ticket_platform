package esame_finale.ticket_platform.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String titolo;

    @Future
    @NotNull
    private LocalDate dataInizio;

    @NotNull
    private String statoLavori;

    @OneToMany(mappedBy="ticket")
    private List<Nota> note;

    @ManyToOne
    @JoinColumn(name = "operatore_id")
    private Operatore operatore;

    @ManyToMany
    @JoinTable(name = "ticket_categorie", 
            joinColumns = @JoinColumn(name = "ticket_id"), 
            inverseJoinColumns = @JoinColumn(name = "categorie_id"))
    private List<Categoria> categorie;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
    }

    public String getStatoLavori() {
        return statoLavori;
    }

    public void setStatoLavori(String statoLavori) {
        this.statoLavori = statoLavori;
    }

    public Operatore getOperatore() {
        return operatore;
    }

    public void setOperatore(Operatore operatore) {
        this.operatore = operatore;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public List<Categoria> getCategorie() {
        return categorie;
    }

    public void setCategorie(List<Categoria> categorie) {
        this.categorie = categorie;
    }

    public List<Nota> getNote() {
        return note;
    }

    public void setNote(List<Nota> note) {
        this.note = note;
    }

    

}
