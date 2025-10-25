package esame_finale.ticket_platform.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.multipart.MultipartFile;

import esame_finale.ticket_platform.model.Operatore;
import esame_finale.ticket_platform.model.Ticket;
import esame_finale.ticket_platform.repository.OperatoreRepository;
import esame_finale.ticket_platform.repository.TicketRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/operatore")
public class OperatoreController {

    @Autowired
    private OperatoreRepository opRepo;

    @Autowired
    private TicketRepository ticRepo;

    @GetMapping("/")
    public String show(Model model) {
        List<Operatore> result = opRepo.findAll();

        for (Operatore op : result) {
            List<Ticket> tickets = op.getTicket();

            if (tickets.isEmpty()) {
                op.setStatoOperatore(true);
            } else {
                boolean tuttiCompletati = true;

                for (Ticket tic : tickets) {
                    if (!"completato".equalsIgnoreCase(tic.getStatoLavori())) {
                        tuttiCompletati = false;
                        break;
                    }
                }

                op.setStatoOperatore(tuttiCompletati);
            }

            opRepo.save(op);
        }

        model.addAttribute("lista", result);
        return "operatori/operatori";
    }


    @GetMapping("/dettaglio/{id}")
    public String show(@PathVariable(name = "id") Integer id, Model model) {
        Optional<Operatore> optOp = opRepo.findById(id);

        if (optOp.isEmpty()) {
            model.addAttribute("empty", true);
            return "operatori/dettaglio";
        }

        Operatore op = optOp.get();
        List<Ticket> tickets = op.getTicket();

        if (tickets.isEmpty()) {
            op.setStatoOperatore(true);
        } else {
            boolean tuttiCompletati = true;

            for (Ticket tic : tickets) {
                if (!"completato".equalsIgnoreCase(tic.getStatoLavori())) {
                    tuttiCompletati = false;
                    break;
                }
            }

            op.setStatoOperatore(tuttiCompletati);
        }

        opRepo.save(op);

        model.addAttribute("operatore", op);
        model.addAttribute("listaTicket", tickets);
        model.addAttribute("empty", false);

        return "operatori/dettaglio";
    }


    @GetMapping("/edit")
    public String edit(Model model){
        Operatore nuovoOperatore = new Operatore();
        nuovoOperatore.setStatoOperatore(true);
        model.addAttribute("operatore", nuovoOperatore);
        model.addAttribute("editMode", false);
        return "operatori/edit";
    }

    @PostMapping("/edit")
    public String create(@Valid @ModelAttribute("operatore") Operatore formOp, 
                        BindingResult bindingResult, @RequestParam(name="foto", required=false) MultipartFile file){

        if (file != null && !file.isEmpty()) {
            try {
                String percorsoUpload = "src/main/resources/static/img/";
                String nomeFile = file.getOriginalFilename();
                Path percorso = Paths.get(percorsoUpload + nomeFile);
                Files.copy(file.getInputStream(), percorso, StandardCopyOption.REPLACE_EXISTING);

                formOp.setPercorsoImg("/img/" + nomeFile);

            } catch (IOException e) {
                bindingResult.addError(new FieldError("operatore", "percorsoImg", "Errore caricamento file"));
            }
        } else {
            formOp.setPercorsoImg("scr/main/resources/static/img/defauls.png");
        }
        
        if(bindingResult.hasErrors()){
            return "operatori/edit";
        }

        opRepo.save(formOp);
        return "redirect:/operatore/";
    }

    @GetMapping("edit/{id}")
    public String modify(@PathVariable("id") Integer id, Model model){
        Operatore op = opRepo.findById(id).get();
        model.addAttribute("editMode", true);
        model.addAttribute("operatore", op);
        return "operatori/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("operatore") Operatore formOp, 
                        BindingResult bindingResult, Model model, 
                        @RequestParam(name="foto", required=false) MultipartFile file){
        Operatore originale = opRepo.findById(formOp.getId()).get();

        if(formOp.getPassword() == null || formOp.getPassword().isBlank()){
            formOp.setPassword(originale.getPassword());
        }
        
        if (file != null && !file.isEmpty()) {
            try {
                String percorsoUpload = "src/main/resources/static/img/";
                String nomeFile = file.getOriginalFilename();
                Path percorso = Paths.get(percorsoUpload + nomeFile);
                Files.copy(file.getInputStream(), percorso, StandardCopyOption.REPLACE_EXISTING);

                formOp.setPercorsoImg("/img/" + nomeFile);

            } catch (IOException e) {
                bindingResult.addError(new FieldError("operatore", "percorsoImg", "Errore caricamento file"));
            }
        } else {
            formOp.setPercorsoImg("scr/main/resources/static/img/defauls.png");
        }

        if(!formOp.getStatoOperatore()){
            bindingResult.addError(new FieldError("operatore", "statoOperatore", "Operatore non disponibile"));
        }

        if(bindingResult.hasErrors()){
            model.addAttribute("editMode", true);
            return "operatori/edit";
        }

        opRepo.save(formOp);
        return "redirect:/operatore/dettaglio/" + formOp.getId();

    }

    @PostMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer id){
        List<Ticket> ticket = opRepo.findById(id).get().getTicket();
        for(Ticket tic : ticket){
            ticRepo.delete(tic);
        }
        opRepo.deleteById(id);
        return "redirect:/operatore/";
    }
}
