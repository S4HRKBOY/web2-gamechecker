package de.fhdo.eborrow.restapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.fhdo.eborrow.controller.wrapper.FilterInfo;
import de.fhdo.eborrow.controller.wrapper.Query;
import de.fhdo.eborrow.services.GameSearchService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import de.fhdo.eborrow.dto.AccountDto;
import de.fhdo.eborrow.dto.GameDto;
import de.fhdo.eborrow.dto.RichGameDto;
import de.fhdo.eborrow.services.AccountService;
import de.fhdo.eborrow.services.GameService;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
public class GameRestController {

    private GameService gameService;
    private GameSearchService gameSearchService;

    @Autowired
    public GameRestController(GameService gameService, GameSearchService gameSearchService) {
        this.gameService = gameService;
        this.gameSearchService = gameSearchService;
    }

    @GetMapping(value = "/home", produces = { "application/json", "application/xml" })
    @ResponseStatus(HttpStatus.OK)
    public List<GameDto> getAll() {
        return gameService.getAll();
    }

    @GetMapping(value = "/game/{id}", produces = { "application/json", "application/xml" })
    @ResponseStatus(HttpStatus.OK)
    public RichGameDto getRichGameById(@PathVariable Long id) {
        return gameService.getRichGameById(id);
    }

    @GetMapping(value = "/game/get-game/{id}", produces = { "application/json", "application/xml" })
    @ResponseStatus(HttpStatus.OK)
    public GameDto getGameById(@PathVariable Long id) {
        return gameService.getGameById(id);
    }

    @DeleteMapping(value = "/game/delete-game/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGameById(@PathVariable Long id) {
        gameService.deleteGameById(id);
    }

    @PostMapping(value = "/game/create-game", produces = { "application/json", "application/xml" }, consumes = {
            "application/json", "application/xml", })
    @ResponseStatus(HttpStatus.CREATED)
    public Long createGame(@Valid @RequestBody GameDto gameDto) {
        List<String> reason = new ArrayList<>();
        if (gameDto.getTitle() == null || gameDto.getTitle().isEmpty()) {
            reason.add(" Spieletitel");
        }
        if (gameDto.getDescription() == null || gameDto.getDescription().isEmpty()) {
            reason.add(" Beschreibung");
        }
        if (gameDto.getPlatforms() == null || gameDto.getPlatforms().isEmpty()) {
            reason.add(" Plattform");
        }
        if (gameDto.getGenres() == null || gameDto.getGenres().isEmpty()) {
            reason.add(" Genre");
        }
        if (gameDto.getPublicationDate() == null) {
            reason.add(" Veröffentlichungsdatum");
        }
        if (gameDto.getAgeRating() == null || gameDto.getAgeRating().isEmpty()) {
            reason.add(" Altersfreigabe");
        }
        if (gameDto.getDeveloper() == null || gameDto.getDeveloper().isEmpty()) {
            reason.add(" Developer");
        }
        if (gameDto.getPublisher() == null || gameDto.getPublisher().isEmpty()) {
            reason.add(" Publisher");
        }
        if (reason.size() != 0) {
            String result = String.join(",", reason);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Folgende Felder dürfen nicht leer sein:" + result + ".");
        }
        return gameService.createGame(gameDto);
    }

    @PutMapping(value = "/game/update-game/{id}", consumes = { "application/json", "application/xml" })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateGame(@PathVariable Long id, @RequestBody GameDto gameDto) {
        if (id != null && gameDto.getId() != null && gameDto.getId().equals(id)) {
            gameService.updateGame(gameDto);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "PathVariable ID und ID im Request Body stimmen nicht überein.");
        }
    }

    @PostMapping(value = "/game/filtered-games", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GameDto> getFilteredGames(@RequestBody FilterInfo filterInfo) throws NotFoundException {
        return gameSearchService.getGamesByFilterInfo(filterInfo);
    }

    @PostMapping(value = "/game/games-search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GameDto> getGamesByQuery(@RequestBody Query query) {
        if (Objects.equals(query.getQuery(), "")) {
            return gameService.getAll();
        }
        return gameSearchService.getGamesBySearchQuery(query.getQuery());
    }

    @GetMapping(value = "/game/all-platforms", produces = { "application/json", "application/xml" })
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllPlatforms() {
        return gameService.getAllPlatforms();
    }

    @GetMapping(value = "/game/all-genres", produces = { "application/json", "application/xml" })
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllGenres() {
        return gameService.getAllGenres();
    }

    @GetMapping(value = "/game/all-age-ratings", produces = { "application/json", "application/xml" })
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllAgeRatings() {
        return gameService.getAllAgeRatings();
    }
}
