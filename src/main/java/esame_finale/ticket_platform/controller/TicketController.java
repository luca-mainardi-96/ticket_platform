package esame_finale.ticket_platform.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import esame_finale.ticket_platform.model.Categoria;
import esame_finale.ticket_platform.model.Nota;
import esame_finale.ticket_platform.model.Operatore;
import esame_finale.ticket_platform.model.Ticket;
import esame_finale.ticket_platform.repository.CategoriaRepository;
import esame_finale.ticket_platform.repository.NotaRepository;
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

    @Autowired
    NotaRepository notaRepo;

    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Integer id, Model model){
        Ticket ticket = ticketRepo.findById(id).get();
        Categoria categorie = ticketRepo.findById(id).get().getCategoria();
        List<Nota> note = ticketRepo.findById(id).get().getNote();
        model.addAttribute("ticket",ticket);
        model.addAttribute("categorie", categorie);
        model.addAttribute("note", note);
        return "ticket/ticket";
    }

    @PostMapping("/{id}")
    public String updateWork(@PathVariable("id") Integer id, 
                            @Valid @ModelAttribute("ticket") Ticket aggiornaStato, 
                            BindingResult bindingResult){
        
        Ticket ticket = ticketRepo.findById(id).get();
                        
        if(aggiornaStato.getStatoLavori().equals(ticket.getStatoLavori())){
            bindingResult.addError(new FieldError("ticket", "statoLavori", "scegli un campo diverso dal precedente"));
        }
        
        if(aggiornaStato.getStatoLavori().equals("")){
            bindingResult.addError(new FieldError("ticket", "statoLavori", "scegli un'opzione valida"));
        }

        if(bindingResult.hasErrors()){
    
            return "ticket/ticket";
        }

        ticket.setStatoLavori(aggiornaStato.getStatoLavori());
        
        Operatore operatore = ticket.getOperatore();

        if(aggiornaStato.getStatoLavori().equalsIgnoreCase("completato")){
            operatore.setStatoOperatore(true);
            opRepo.save(operatore);
        }

        ticketRepo.save(ticket);

        return "redirect:/operatore/dettaglio/" + operatore.getId();
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

        if(!formTicket.getOperatore().getStatoOperatore()){
            bindingResult.addError(new FieldError("ticket", "operatore", "operatore non disponibile"));
        }

        if(bindingResult.hasErrors()){
            model.addAttribute("operLista", opRepo.findAll());
            model.addAttribute("catLista", catRepo.findAll());
            return "ticket/edit";
        }

        ticketRepo.save(formTicket);

        Operatore operatore = formTicket.getOperatore();

        if(!formTicket.getStatoLavori().equalsIgnoreCase("completato")){
            operatore.setStatoOperatore(false);
        }

        opRepo.save(operatore);
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

        if(!tic.getOperatore().getStatoOperatore()){
            bindingResult.addError(new FieldError("ticket", "operatore", "operatore non disponibile"));
        }

        if(bindingResult.hasErrors()){
            model.addAttribute("operLista", opRepo.findAll());
            model.addAttribute("catLista", catRepo.findAll());
            return "ticket/edit";
        }
        ticketRepo.save(tic);

        Operatore operatore = tic.getOperatore();

        if(!tic.getStatoLavori().equalsIgnoreCase("completato")){
            operatore.setStatoOperatore(false);
        }

        opRepo.save(operatore);
        return "redirect:/dashboard/";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id){
        Ticket ticket = ticketRepo.findById(id).get();
        List<Nota> note = ticket.getNote();
        for(Nota n : note){
            notaRepo.delete(n);
        }
        ticketRepo.deleteById(id);
        return "redirect:/dashboard/";
    }

    @PostMapping("/{id}/note")
    public String addNota(@PathVariable("id") Integer id,
                        @RequestParam("testo") String testo,
                        Authentication aut) {

        Ticket ticket = ticketRepo.findById(id).get();
        Nota nota = new Nota();
        if(aut != null){
            nota.setAutore(aut.getName());
        } else {
            nota.setAutore("Utente anonimo");
        }
        nota.setNota(testo.trim());
        nota.setDataCreazione(LocalDate.now());
        nota.setTicket(ticket);
        notaRepo.save(nota);

        return "redirect:/ticket/" + id;
    }

}
