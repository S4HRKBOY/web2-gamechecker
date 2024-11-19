package de.fhdo.eborrow.restapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.fhdo.eborrow.dto.AccountDto;
import de.fhdo.eborrow.dto.GameDto;
import de.fhdo.eborrow.dto.ReviewDto;
import de.fhdo.eborrow.services.AccountService;
import de.fhdo.eborrow.services.GameService;
import de.fhdo.eborrow.services.ReviewService;

@RestController
@RequestMapping("/game")
public class GameRestController {

    private GameService gameService; 
    private ReviewService reviewService; 
    private AccountService accountService; 

    @Autowired
    public GameRestController(GameService gameService, ReviewService reviewService, AccountService accountService) {
        this.gameService = gameService; 
        this.reviewService = reviewService; 
        this.accountService = accountService; 
    }

    @GetMapping("/getGames")
    @ResponseStatus(HttpStatus.OK)
    public List<GameDto> getAll() {
        return gameService.getAll(); 
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GameDto getGameById(@PathVariable Long id) {
        return gameService.getGameById(id); 
    }

    @DeleteMapping("/deleteGameById/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGameById(@PathVariable Long id) {
        gameService.deleteGameById(id);
    }

    @PutMapping("/createGame")
    @ResponseStatus(HttpStatus.CREATED)
    public void createGame(@RequestBody GameDto gameDto) {
        gameService.createGame(gameDto); 
    }

    @PostMapping("/updateGame")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateGame(@RequestBody GameDto gameDto) {
        gameService.updateGame(gameDto); 
    }

    // @GetMapping("/getReviewsByGameId/{id}")
    // @ResponseStatus(HttpStatus.OK)
    // public List<ReviewDto> getReviewsByGameId(@PathVariable Long id) {
    //     return reviewService.getAllReviewsToGame(id); 
    // }

    //TODO später löschen
    @GetMapping("/getReviewById/{id}")
    public ReviewDto getReviewById(@PathVariable Long id) {
        return reviewService.getReviewById(id); 
    }

    @GetMapping("/getAccountById/{id}")
    public AccountDto getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id); 
    }
}
