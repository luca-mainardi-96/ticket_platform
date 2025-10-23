package esame_finale.ticket_platform.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import esame_finale.ticket_platform.model.Categoria;
import esame_finale.ticket_platform.model.Nota;
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

    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Integer id, Model model){
        Ticket ticket = ticketRepo.findById(id).get();
        List<Categoria> categorie = ticketRepo.findById(id).get().getCategorie();
        List<Nota> note = ticketRepo.findById(id).get().getNote();
        model.addAttribute("ticket",ticket);
        model.addAttribute("categorie", categorie);
        model.addAttribute("note", note);
        return "ticket/ticket";
    }

    @PostMapping("/{id}")
    public String updateWork(){
        return null;
    }

    @GetMapping("/edit")
    public String create(Model model){
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("operLista", opRepo.findAll());
        model.addAttribute("catLista", catRepo.findAll());
        model.addAttribute("editMode", false);
        return "ticket/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute("ticket") Ticket formTicket, BindingResult bindingResult, 
                        Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("operLista", opRepo.findAll());
            model.addAttribute("catLista", catRepo.findAll());
            return "ticket/edit";
        }

        ticketRepo.save(formTicket);
        return "redirect:/dashboard/";
    }

    @GetMapping("/edit/{id}")
    public String modify(@PathVariable("id") Integer id, Model model){
        Ticket tic = ticketRepo.findById(id).get();
        model.addAttribute("editMode", true);
        model.addAttribute("ticket", tic);
        return "ticket/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("ticket") Ticket tic, 
                        BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("operLista", opRepo.findAll());
            model.addAttribute("catLista", catRepo.findAll());
            return "ticket/edit";
        }
        ticketRepo.save(tic);
        return "redirect:/dashboard/";
    }
}
