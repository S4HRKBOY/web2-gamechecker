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
import de.fhdo.eborrow.dto.GameDto;
import de.fhdo.eborrow.dto.ReviewDto;
import de.fhdo.eborrow.services.GameService;
import de.fhdo.eborrow.services.ReviewService;

@Controller
@RequestMapping("/thymeleaf/game")
public class GameController {

    private GameService gameService; 
    private ReviewService reviewService; 

    @Autowired
    public GameController(GameService gameService, ReviewService reviewService) {
        this.gameService = gameService; 
        this.reviewService = reviewService; 
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
        List<ReviewDto> reviewDto = reviewService.getAllReviewsToGame(id); 
        model.addAttribute("game", gameDto); 
        model.addAttribute("reviews", reviewDto); 
        return "detail_page";
    }

}
