package de.fhdo.eborrow.controller;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    private GameService gameService;
    private AccountService accountService;
    private ReviewService reviewService;

    @Autowired
    public GameController(GameService gameService, AccountService accountService, ReviewService reviewService) {
        this.gameService = gameService;
        this.accountService = accountService;
        this.reviewService = reviewService;
    }

    @GetMapping("/home")
    public String getAll(Model model) {
        List<GameDto> gameDto = gameService.getAll();
        RichAccountDto accountDto = accountService.getRichAccountById(1L);
        model.addAttribute("games", gameDto);
        model.addAttribute("account", accountDto);
        return "start_page";
    }

    @GetMapping("/game/{id}")
    public String getGameById(@PathVariable Long id, Model model) {
        RichGameDto gameDto = gameService.getGameById(id);
        gameDto.getReviewsDto().sort(Comparator.comparing(ReviewDto::getId).reversed());
        AccountDto accountDto = accountService.getAccountById(1L);
        boolean hasReviewed = reviewService.existsByGameAndAccount(id, accountDto.getId());
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
	public String unlistGameFromAccount(@RequestParam("accountId") Long accountId, @RequestParam("gameId") Long gameId, Model model) {
		accountService.unlistGameFromAccount(accountId, gameId);
        RichGameDto gameDto = gameService.getGameById(gameId);
        gameDto.getReviewsDto().sort(Comparator.comparing(ReviewDto::getId).reversed());
        AccountDto accountDto = accountService.getAccountById(accountId);
        boolean hasReviewed = reviewService.existsByGameAndAccount(gameId, accountId);
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
	public String addGameToAccount(@RequestParam("accountId") Long accountId, @RequestParam("gameId") Long gameId, Model model) {
		accountService.addGameToAccount(accountId, gameId);
        RichGameDto gameDto = gameService.getGameById(gameId);
        gameDto.getReviewsDto().sort(Comparator.comparing(ReviewDto::getId).reversed());
        AccountDto accountDto = accountService.getAccountById(accountId);
        boolean hasReviewed = reviewService.existsByGameAndAccount(gameId, accountId);
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
