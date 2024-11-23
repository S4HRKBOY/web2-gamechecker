package de.fhdo.eborrow.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import de.fhdo.eborrow.converters.GameMapper;
import de.fhdo.eborrow.domain.Game;
import de.fhdo.eborrow.dto.RichGameDto;
import de.fhdo.eborrow.dto.RichAccountDto;
import de.fhdo.eborrow.dto.ReviewDto;
import de.fhdo.eborrow.services.AccountService;
import de.fhdo.eborrow.services.GameService;


@Controller
@RequestMapping("/thymeleaf")
public class GameController {

    private GameService gameService; 
    private AccountService accountService;  

    @Autowired
    public GameController(GameService gameService, AccountService accountService) {
        this.gameService = gameService; 
        this.accountService = accountService; 
    }

    @GetMapping("/home")
    public String getAll(Model model) {
        List<RichGameDto> gameDto = gameService.getAll();
        model.addAttribute("games", gameDto);
        return "start_page";
    }

    @GetMapping("/game/{id}")
    public String getGameById(@PathVariable Long id, Model model) {
        RichGameDto gameDto = gameService.getGameById(id); 
        RichAccountDto accountDto = accountService.getRichAccountById(1L); 
        model.addAttribute("game", gameDto); 
        model.addAttribute("account", accountDto);  
        return "detail_page";
    }

}
