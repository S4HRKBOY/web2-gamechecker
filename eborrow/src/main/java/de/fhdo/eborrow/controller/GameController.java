package de.fhdo.eborrow.controller;

import java.util.List;

import de.fhdo.eborrow.services.GameSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import de.fhdo.eborrow.converters.GameMapper;
import de.fhdo.eborrow.domain.Game;
import de.fhdo.eborrow.dto.GameDto;
import de.fhdo.eborrow.services.GameService;

@Controller
@RequestMapping("/thymeleaf/game")
public class GameController {

    private GameService gameService; 

    GameSearchService gameSearchService;

    @Autowired
    public GameController(GameService gameService, GameSearchService gameSearchService) {
        this.gameService = gameService;
        this.gameSearchService = gameSearchService;
    }

    @GetMapping("/allGames")
    public String getAll(Model model) {
        List<GameDto> gameDto = gameService.getAll(); 
        model.addAttribute("games", gameDto);
        return "start_page";
    }

    @GetMapping("/{id}")
    public String getGameById(@PathVariable Long id, Model model) {
        GameDto gameDto = gameService.getGameById(id); 
        model.addAttribute("game", gameDto); 
        return "detail_page";
    }


}
