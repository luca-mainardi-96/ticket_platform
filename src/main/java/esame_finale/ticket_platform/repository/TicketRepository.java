package esame_finale.ticket_platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import esame_finale.ticket_platform.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer>{

}
