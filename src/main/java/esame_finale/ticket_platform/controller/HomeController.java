package esame_finale.ticket_platform.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import esame_finale.ticket_platform.model.Operatore;
import esame_finale.ticket_platform.repository.OperatoreRepository;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private OperatoreRepository opRepo;

    @GetMapping("")
    public String list(Model model){
        List<Operatore> lista = opRepo.findAll();
        model.addAttribute("lista", lista);
        return "/home/home";
    }
}
