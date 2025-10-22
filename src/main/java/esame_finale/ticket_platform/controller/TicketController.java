package esame_finale.ticket_platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import esame_finale.ticket_platform.model.Ticket;
import esame_finale.ticket_platform.repository.CategoriaRepository;
import esame_finale.ticket_platform.repository.OperatoreRepository;
import esame_finale.ticket_platform.repository.TicketRepository;

@Controller
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    TicketRepository ticketRepo;

    @Autowired
    OperatoreRepository opRepo;

    @Autowired
    CategoriaRepository catRepo;

    @GetMapping("/edit")
    public String create(Model model){
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("operLista", opRepo.findAll());
        model.addAttribute("catLista", catRepo.findAll());
        return "ticket/edit";
    }

}
