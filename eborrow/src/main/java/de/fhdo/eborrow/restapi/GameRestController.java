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
import de.fhdo.eborrow.dto.RichGameDto;
import de.fhdo.eborrow.dto.ReviewDto;
import de.fhdo.eborrow.dto.RichAccountDto;
import de.fhdo.eborrow.services.AccountService;
import de.fhdo.eborrow.services.GameService;
import de.fhdo.eborrow.services.ReviewService;

@RestController
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

    @GetMapping("/home")
    @ResponseStatus(HttpStatus.OK)
    public List<RichGameDto> getAll() {
        return gameService.getAll(); 
    }

    @GetMapping("/game/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RichGameDto getGameById(@PathVariable Long id) {
        return gameService.getGameById(id); 
    }

    @DeleteMapping("/game/delete-game/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGameById(@PathVariable Long id) {
        gameService.deleteGameById(id);
    }

    @PutMapping("/game/create-game")
    @ResponseStatus(HttpStatus.CREATED)
    public void createGame(@RequestBody RichGameDto gameDto) {
        gameService.createGame(gameDto); 
    }

    @PostMapping("/game/update-game")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateGame(@RequestBody RichGameDto gameDto) {
        gameService.updateGame(gameDto); 
    }

    @GetMapping("/game/all-platforms")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllPlatforms() {
        return gameService.getAllPlatforms(); 
    }

    @GetMapping("/game/all-genres")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllGenres() {
        return gameService.getAllGenres(); 
    }

    @GetMapping("/game/all-age-ratings")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllAgeRatings() {
        return gameService.getAllAgeRatings(); 
    }



    //TODO später löschen
    @GetMapping("/getReviewById/{id}")
    public ReviewDto getReviewById(@PathVariable Long id) {
        return reviewService.getReviewById(id); 
    }

    @GetMapping("/getAccountById/{id}")
    public AccountDto getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id); 
    }

    @GetMapping("/getRichAccounts")
    public List<RichAccountDto> getRichAccounts() {
        return accountService.getRichAccounts(); 
    }

}
