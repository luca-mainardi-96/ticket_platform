package esame_finale.ticket_platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import esame_finale.ticket_platform.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{

    public Categoria findByNomeContainingIgnoreCase(String nome);
}
