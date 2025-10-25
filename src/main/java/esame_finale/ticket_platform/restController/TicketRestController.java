package esame_finale.ticket_platform.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import esame_finale.ticket_platform.model.Ticket;
import esame_finale.ticket_platform.repository.CategoriaRepository;
import esame_finale.ticket_platform.repository.TicketRepository;

@RestController
@CrossOrigin
@RequestMapping("/api/ticket")
public class TicketRestController {

    @Autowired
    private TicketRepository ticketRepo;

    @Autowired
    private CategoriaRepository catRepo;

    @GetMapping("/all")
    public List<Ticket> listaCompleta(){
        List<Ticket> result = ticketRepo.findAll();
        return result;
    }

    @GetMapping("/filter/stato")
    public List<Ticket> filterStato(@RequestParam(name="keyword", required=false) String keyword){
        List<Ticket> result = null;
        if(keyword != null && !keyword.isBlank()){
            result = ticketRepo.findByStatoLavoriContainingIgnoreCase(keyword);
        } else {
            result = ticketRepo.findAll();
        }
        return result;
    }

    @GetMapping("/filter/categoria")
    public List<Ticket> filterCategoria(@RequestParam(name="keyword", required=false) String keyword){
        List<Ticket> result = null;
        if(keyword != null && !keyword.isBlank()){
            result = ticketRepo.findByCategoria(catRepo.findByNomeContainingIgnoreCase(keyword));
        } else {
            result = ticketRepo.findAll();
        }

        return result;
    }
}
