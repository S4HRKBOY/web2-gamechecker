package de.fhdo.eborrow.restapi;

import java.util.List;
import java.util.Objects;

import de.fhdo.eborrow.controller.wrapper.FilterInfo;
import de.fhdo.eborrow.controller.wrapper.Query;
import de.fhdo.eborrow.services.GameSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
@CrossOrigin
public class GameRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameRestController.class);

    private GameService gameService;
    private GameSearchService gameSearchService;
    private ReviewService reviewService;
    private AccountService accountService;

    @Autowired
    public GameRestController(GameService gameService, ReviewService reviewService, AccountService accountService,
            GameSearchService gameSearchService) {
        this.gameService = gameService;
        this.reviewService = reviewService;
        this.accountService = accountService;
        this.gameSearchService = gameSearchService;
    }

    @GetMapping("/home")
    @ResponseStatus(HttpStatus.OK)
    public List<GameDto> getAll() {
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

    @PostMapping("/game/create-game")
    @ResponseStatus(HttpStatus.CREATED)
    public void createGame(@RequestBody GameDto gameDto) {
        gameService.createGame(gameDto);
    }

    @PutMapping("/game/update-game/{id}")
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
    public List<GameDto> getFilteredGames(@RequestBody FilterInfo filterInfo) {
        AccountDto accountDto = accountService.getAccountById(1L);
        List<GameDto> games = gameService.getAll();

        if (!Objects.equals(filterInfo.getGenre(), "All")) {
            games = games.stream().filter(gameDto -> gameDto.getGenres().contains(filterInfo.getGenre())).toList();
        }

        if (filterInfo.getDeveloper() != null && !filterInfo.getDeveloper().isEmpty() && !filterInfo.getDeveloper().isBlank()) {
            games = games.stream().filter(gameDto -> gameDto.getDeveloper().equalsIgnoreCase(filterInfo.getDeveloper())).toList();
        }

        if (!Objects.equals(filterInfo.getPlatform(), "All")) {
            games = games.stream().filter(gameDto -> gameDto.getPlatforms().contains(filterInfo.getPlatform())).toList();
        }

        return games;
    }

    @PostMapping(value = "/game/games-search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GameDto> getGamesByQuery(@RequestBody Query query) {
        if (Objects.equals(query.getQuery(), "")) {
            return gameService.getAll();
        }
        return gameSearchService.getGamesBySearchQuery(query.getQuery());
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

    // TODO später löschen
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
