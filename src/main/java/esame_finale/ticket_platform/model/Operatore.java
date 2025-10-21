package esame_finale.ticket_platform.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

@Entity
public class Operatore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    private String email;

    @NotNull
    private Boolean statoOperatore;

    @OneToMany(mappedBy = "operatore")
    private List<Ticket> ticket;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Ruolo> ruoli;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getStatoOperatore() {
        return statoOperatore;
    }

    public void setStatoOperatore(Boolean statoOperatore) {
        this.statoOperatore = statoOperatore;
    }

    public List<Ticket> getTicket() {
        return ticket;
    }

    public void setTickets(List<Ticket> ticket) {
        this.ticket = ticket;
    }

    public List<Ruolo> getRuoli() {
        return ruoli;
    }

    public void setRuoli(List<Ruolo> ruoli) {
        this.ruoli = ruoli;
    }

    
}
