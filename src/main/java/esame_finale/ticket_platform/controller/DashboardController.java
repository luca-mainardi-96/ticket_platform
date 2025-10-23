package esame_finale.ticket_platform.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import esame_finale.ticket_platform.model.Operatore;
import esame_finale.ticket_platform.model.Ticket;
import esame_finale.ticket_platform.repository.OperatoreRepository;
import esame_finale.ticket_platform.repository.TicketRepository;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private TicketRepository ticketRepo;

    @Autowired
    private OperatoreRepository opRepo;
    
    @GetMapping("/")
    public String filter(@RequestParam(name="keyword", required=false) String keyword, Model model){
        List<Ticket> result;
        List<Operatore> opList = opRepo.findAll();
        if(keyword == null || keyword.isBlank()){
            result = ticketRepo.findAll();
        } else{
            result = ticketRepo.findByTitoloContainingIgnoreCase(keyword);
        }

        model.addAttribute("lista", result);
        model.addAttribute("operatori", opList);
        
        return "dashboard/home";
    }
}
