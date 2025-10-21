package esame_finale.ticket_platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import esame_finale.ticket_platform.model.Operatore;
import esame_finale.ticket_platform.repository.OperatoreRepository;

@Controller
@RequestMapping("/operatore")
public class OperatoreController {

    @Autowired
    private OperatoreRepository opRepo;

    @GetMapping("/details/{id}")
    public String show(@PathVariable("id") Integer id, Model model){
        Operatore op = opRepo.findById(id).get();                                 //da rivedere
        model.addAttribute("editMode", true);
        model.addAttribute("operatore", op);
        return "/operatori/dettaglio";
    }


}
