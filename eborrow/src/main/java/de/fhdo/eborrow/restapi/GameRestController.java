package de.fhdo.eborrow.restapi;

import java.util.List;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import de.fhdo.eborrow.dto.AccountDto;
import de.fhdo.eborrow.dto.GameDto;
import de.fhdo.eborrow.dto.RichGameDto;
import de.fhdo.eborrow.dto.ReviewDto;
import de.fhdo.eborrow.dto.RichAccountDto;
import de.fhdo.eborrow.services.AccountService;
import de.fhdo.eborrow.services.GameService;
import de.fhdo.eborrow.services.ReviewService;

@RestController
public class GameRestController {

    private GameService gameService;

    @Autowired
    public GameRestController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping(value="/home", produces={"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public List<GameDto> getAll() {
        return gameService.getAll();
    }

    @GetMapping(value="/game/{id}", produces={"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public RichGameDto getGameById(@PathVariable Long id) {
        return gameService.getGameById(id);
    }

    @DeleteMapping(value="/game/delete-game/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGameById(@PathVariable Long id) {
        gameService.deleteGameById(id);
    }

    @PostMapping(value="/game/create-game", consumes={"application/json","application/xml"})
    @ResponseStatus(HttpStatus.CREATED)
    public void createGame(@RequestBody GameDto gameDto) {
        gameService.createGame(gameDto);
    }

    @PutMapping(value="/game/update-game/{id}", consumes={"application/json","application/xml"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateGame(@PathVariable Long id, @RequestBody GameDto gameDto) {
        if (id != null && gameDto.getId() != null && gameDto.getId().equals(id)) {
            gameService.updateGame(gameDto);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "PathVariable ID und ID im Request Body stimmen nicht überein.");
        }
    }

    @GetMapping(value="/game/all-platforms", produces={"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllPlatforms() {
        return gameService.getAllPlatforms();
    }

    @GetMapping(value="/game/all-genres", produces={"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllGenres() {
        return gameService.getAllGenres();
    }

    @GetMapping(value="/game/all-age-ratings", produces={"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllAgeRatings() {
        return gameService.getAllAgeRatings();
    }
}
