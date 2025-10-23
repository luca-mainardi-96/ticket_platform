package esame_finale.ticket_platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import esame_finale.ticket_platform.model.Ticket;
import esame_finale.ticket_platform.repository.CategoriaRepository;
import esame_finale.ticket_platform.repository.OperatoreRepository;
import esame_finale.ticket_platform.repository.TicketRepository;
import jakarta.validation.Valid;

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

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute("ticket") Ticket formTicket, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "ticket/edit";
        }

        ticketRepo.save(formTicket);
        return "redirect:/ticket";
    }

    @GetMapping("/edit/{id}")
    public String modify(@PathVariable("id") Integer id, Model model){
        Ticket tic = ticketRepo.findById(id).get();
        model.addAttribute("editMode", true);
        model.addAttribute("ticket", tic);
        return null;
    }

}
