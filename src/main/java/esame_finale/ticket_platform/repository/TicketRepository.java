package esame_finale.ticket_platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import esame_finale.ticket_platform.model.Operatore;
import esame_finale.ticket_platform.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer>{

    public List<Ticket> findByOperatore(Operatore operatore);

    public List<Ticket> findByTitoloContainingIgnoreCase(String titolo);
}
