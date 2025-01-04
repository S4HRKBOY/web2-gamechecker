package de.fhdo.eborrow.controller;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import de.fhdo.eborrow.controller.wrapper.FilterInfo;
import de.fhdo.eborrow.controller.wrapper.Query;
import de.fhdo.eborrow.services.GameSearchService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import de.fhdo.eborrow.dto.RichGameDto;
import de.fhdo.eborrow.dto.GameDto;
import de.fhdo.eborrow.dto.RichAccountDto;
import de.fhdo.eborrow.dto.AccountDto;
import de.fhdo.eborrow.dto.ReviewDto;
import de.fhdo.eborrow.services.AccountService;
import de.fhdo.eborrow.services.GameService;
import de.fhdo.eborrow.services.ReviewService;

@Controller
@RequestMapping("/thymeleaf")
public class GameController {

    private final static Logger LOGGER = LoggerFactory.getLogger(GameController.class);

    private GameService gameService;
    private AccountService accountService;
    private ReviewService reviewService;
    private GameSearchService gameSearchService;

    private Long accId = 1L; 

    @Autowired
    public GameController(GameService gameService, AccountService accountService, ReviewService reviewService,
            GameSearchService gameSearchService) {
        this.gameService = gameService;
        this.accountService = accountService;
        this.reviewService = reviewService;
        this.gameSearchService = gameSearchService;
    }

    @GetMapping("/home")
    public String getAll(Model model) throws NotFoundException {
        List<GameDto> gameDto = gameService.getAll();
        RichAccountDto accountDto = accountService.getRichAccountById(accId);
        List<String> genres = gameService.getAllGenres(); 
        List<String> platforms = gameService.getAllPlatforms(); 
        model.addAttribute("genres", genres); 
        model.addAttribute("platforms", platforms); 
        model.addAttribute("FilterInfo", new FilterInfo());
        model.addAttribute("Query", new Query());
        model.addAttribute("games", gameDto);
        model.addAttribute("account", accountDto);
        return "start_page";
    }

    @PostMapping("/game/filtered-games")
    public String getFilteredGames(@ModelAttribute FilterInfo filterInfo, Model model) throws NotFoundException {
        LOGGER.info(filterInfo.toString());
        AccountDto accountDto = accountService.getAccountById(accId);
        List<GameDto> games = gameService.getAll();

        if(!Objects.equals(filterInfo.getGenre(), "All")){
            games = gameSearchService.gamesByGenre(games, filterInfo.getGenre());
        }

        if(!filterInfo.getDeveloper().isEmpty() && !filterInfo.getDeveloper().isBlank()){
            games = gameSearchService.gamesByDeveloper(games, filterInfo.getDeveloper());
        }

        if(!Objects.equals(filterInfo.getPlatform(), "All")){
            games = gameSearchService.gamesByPlatform(games, filterInfo.getPlatform());
        }

        List<String> genres = gameService.getAllGenres(); 
        List<String> platforms = gameService.getAllPlatforms(); 
        model.addAttribute("genres", genres); 
        model.addAttribute("platforms", platforms); 
        model.addAttribute("games", games);
        model.addAttribute("account", accountDto);
        model.addAttribute("FilterInfo", new FilterInfo());
        model.addAttribute("Query", new Query());
        return "start_page";
    }

    @PostMapping("/game/games-search")
    public String getGamesByQuery(@ModelAttribute Query query, Model model) throws NotFoundException {
        LOGGER.info(query.getQuery());
        AccountDto accountDto = accountService.getAccountById(accId);
        List<GameDto> games = gameSearchService.getGamesBySearchQuery(query.getQuery());

        List<String> genres = gameService.getAllGenres(); 
        List<String> platforms = gameService.getAllPlatforms(); 
        model.addAttribute("genres", genres); 
        model.addAttribute("platforms", platforms); 
        model.addAttribute("games", games);
        model.addAttribute("account", accountDto);
        model.addAttribute("FilterInfo", new FilterInfo());
        model.addAttribute("Query", new Query());
        return "start_page";
    }

    @GetMapping("/game/{id}")
    public String getGameById(@PathVariable Long id, Model model) throws NotFoundException {
        RichGameDto gameDto = gameService.getRichGameById(id);
        gameDto.getReviewsDto().sort(Comparator.comparing(ReviewDto::getId).reversed());
        AccountDto accountDto = accountService.getAccountById(accId);
        boolean hasReviewed = reviewService.existsByAccountAndGame(accountDto.getId(), id);
        boolean editReview = false; 
        ReviewDto reviewDto = new ReviewDto();
        boolean accountHasGame = accountService.accountHasGame(accountDto.getId(), id); 
        reviewDto.setReviewDate(LocalDate.now());
        model.addAttribute("review", reviewDto); 
        model.addAttribute("game", gameDto);
        model.addAttribute("account", accountDto);
        model.addAttribute("hasReviewed", hasReviewed);
        model.addAttribute("accountHasGame", accountHasGame); 
        model.addAttribute("editReview", editReview);
        return "detail_page";
    }

    @PostMapping("/game/unlist-game")
	public String unlistGameFromAccount(@RequestParam("accountId") Long accountId, @RequestParam("gameId") Long gameId, Model model) throws NotFoundException {
		accountService.unlistGameFromAccount(accountId, gameId);
        RichGameDto gameDto = gameService.getRichGameById(gameId);
        gameDto.getReviewsDto().sort(Comparator.comparing(ReviewDto::getId).reversed());
        AccountDto accountDto = accountService.getAccountById(accountId);
        boolean hasReviewed = reviewService.existsByAccountAndGame(accountId, gameId);
        boolean editReview = false; 
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setReviewDate(LocalDate.now());
        boolean accountHasGame = accountService.accountHasGame(accountId, gameId); 
		model.addAttribute("review", reviewDto); 
        model.addAttribute("game", gameDto);
        model.addAttribute("account", accountDto);
        model.addAttribute("hasReviewed", hasReviewed);
        model.addAttribute("accountHasGame", accountHasGame); 
        model.addAttribute("editReview", editReview);
		return "detail_page";
	}

    @PostMapping("/game/add-game")
	public String addGameToAccount(@RequestParam("accountId") Long accountId, @RequestParam("gameId") Long gameId, Model model) throws NotFoundException {
		accountService.addGameToAccount(accountId, gameId);
        RichGameDto gameDto = gameService.getRichGameById(gameId);
        gameDto.getReviewsDto().sort(Comparator.comparing(ReviewDto::getId).reversed());
        AccountDto accountDto = accountService.getAccountById(accountId);
        boolean hasReviewed = reviewService.existsByAccountAndGame(accountId, gameId);
        boolean editReview = false; 
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setReviewDate(LocalDate.now());
        boolean accountHasGame = accountService.accountHasGame(accountId, gameId); 
		model.addAttribute("review", reviewDto); 
        model.addAttribute("game", gameDto);
        model.addAttribute("account", accountDto);
        model.addAttribute("hasReviewed", hasReviewed);
        model.addAttribute("accountHasGame", accountHasGame); 
        model.addAttribute("editReview", editReview);
		return "detail_page";
	}


}
