package esame_finale.ticket_platform.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message="titolo obbligatorio")
    @NotBlank(message="campo obbligatorio")
    private String titolo;

    @PastOrPresent(message="data successiva a oggi non valida")
    @NotNull(message="seleziona una data d'inizio")
    private LocalDate dataInizio;

    @NotNull
    private String statoLavori;

    @OneToMany(mappedBy="ticket")
    private List<Nota> note;

    @ManyToOne
    @JoinColumn(name = "operatore_id")
    @NotNull(message="seleziona un operatore")
    private Operatore operatore;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    @NotNull(message = "seleziona una categoria")
    private Categoria categoria;


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

    public List<Nota> getNote() {
        return note;
    }

    public void setNote(List<Nota> note) {
        this.note = note;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    

}
