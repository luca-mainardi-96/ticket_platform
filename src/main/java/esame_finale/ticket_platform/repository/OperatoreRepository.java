package esame_finale.ticket_platform.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import esame_finale.ticket_platform.model.Operatore;


public interface OperatoreRepository extends JpaRepository<Operatore, Integer> {

    public Optional<Operatore> findByUsername(String username);

}
