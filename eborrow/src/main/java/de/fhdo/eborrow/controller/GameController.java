package de.fhdo.eborrow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.fhdo.eborrow.services.GameService;

@Controller
public class GameController {

    GameService gameService; 

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService; 
    }

    
}
