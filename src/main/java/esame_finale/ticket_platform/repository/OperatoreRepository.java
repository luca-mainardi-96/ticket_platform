package esame_finale.ticket_platform.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import esame_finale.ticket_platform.model.Operatore;


public interface OperatoreRepository extends JpaRepository<Operatore, Integer> {

    public List<Operatore> findByUsernameContainingIgnoreCase(String username);

    public Optional<Operatore> findByUsername(String username);

}
