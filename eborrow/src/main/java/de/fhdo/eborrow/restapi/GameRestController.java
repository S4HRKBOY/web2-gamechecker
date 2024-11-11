package de.fhdo.eborrow.restapi;

import java.util.List;
import java.util.Set;

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

import de.fhdo.eborrow.domain.Review;
import de.fhdo.eborrow.dto.GameDto;
import de.fhdo.eborrow.dto.ReviewDto;
import de.fhdo.eborrow.services.GameService;
import de.fhdo.eborrow.services.ReviewService;

@RestController
@RequestMapping("/game")
public class GameRestController {

    private GameService gameService; 
    private ReviewService reviewService; 

    @Autowired
    public GameRestController(GameService gameService, ReviewService reviewService) {
        this.gameService = gameService; 
        this.reviewService = reviewService; 
    }

    @GetMapping("/allGames")
    @ResponseStatus(HttpStatus.OK)
    public List<GameDto> getAll() {
        return gameService.getAll(); 
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GameDto getGameById(@PathVariable Long id) {
        return gameService.getGameById(id); 
    }

    @DeleteMapping("/deleteOne/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGameById(@PathVariable Long id) {
        gameService.deleteGame(id);
    }

    @PutMapping("/createOne")
    @ResponseStatus(HttpStatus.CREATED)
    public void createGame(@RequestBody GameDto gameDto) {
        gameService.addGame(gameDto); 
    }

    @PostMapping("/updateOne")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateGame(@RequestBody GameDto gameDto) {
        gameService.updateGame(gameDto); 
    }

    @GetMapping("/getReviewsByGameId/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Review> getReviewsByGameId(@PathVariable Long id) {
        return gameService.getReviewsByGameId(id); 
    }

    @GetMapping("/getReviewById/{id}")
    public ReviewDto getReviewById(@PathVariable Long id) {
        return reviewService.getReviewById(id); 
    }

}
