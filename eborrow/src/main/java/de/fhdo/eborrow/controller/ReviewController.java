package de.fhdo.eborrow.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import de.fhdo.eborrow.dto.AccountDto;
import de.fhdo.eborrow.dto.ReviewDto;
import de.fhdo.eborrow.dto.RichGameDto;
import de.fhdo.eborrow.services.AccountService;
import de.fhdo.eborrow.services.GameService;
import de.fhdo.eborrow.services.ReviewService;

@Controller
@RequestMapping("/thymeleaf/review")
public class ReviewController {

    private GameService gameService;
    private AccountService accountService;
    private ReviewService reviewService;

    @Autowired
    public ReviewController (GameService gameService, AccountService accountService, ReviewService reviewService) {
        this.gameService = gameService;
        this.accountService = accountService;
        this.reviewService = reviewService;
    }

    @PostMapping("/create-review")
    public String createReview(@ModelAttribute ReviewDto reviewDto, @RequestParam("gameId") Long gameId, @RequestParam("accountId") Long accountId, Model model) {
        reviewService.addReview(reviewDto, gameId, accountId);
        model.addAttribute("review", reviewDto);
        RichGameDto gameDto = gameService.getGameById(gameId);
        AccountDto accountDto = accountService.getAccountById(1L);
        boolean hasReviewed = reviewService.existsByGameAndAccount(gameId, accountDto.getId());
        boolean editReview = false; 
        boolean accountHasGame = accountService.accountHasGame(accountId, gameId); 
        model.addAttribute("game", gameDto);
        model.addAttribute("account", accountDto);
        model.addAttribute("hasReviewed", hasReviewed);
        model.addAttribute("editReview", editReview);
        model.addAttribute("accountHasGame", accountHasGame); 
        return "detail_page";
    }

    @PostMapping("/delete-review")
    public String deleteReview(@RequestParam("reviewId") Long reviewId, Long gameId, Long accountId, Model model) {
        reviewService.deleteReviewById(reviewId);
        RichGameDto gameDto = gameService.getGameById(gameId);
        AccountDto accountDto = accountService.getAccountById(accountId);
        boolean hasReviewed = reviewService.existsByGameAndAccount(gameId, accountId);
        boolean accountHasGame = accountService.accountHasGame(accountId, gameId); 
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setReviewDate(LocalDate.now());
        boolean editReview = false; 
        model.addAttribute("review", reviewDto); 
        model.addAttribute("game", gameDto);
        model.addAttribute("account", accountDto);
        model.addAttribute("hasReviewed", hasReviewed);
        model.addAttribute("editReview", editReview);
        model.addAttribute("accountHasGame", accountHasGame); 
        return "detail_page";
    }

    @GetMapping("/update-review/{reviewId}")
    public String getUpdateReview(@PathVariable("reviewId") Long reviewId, Long gameId, Long accountId, Model model) {
        ReviewDto reviewDto = reviewService.getReviewById(reviewId);
        RichGameDto gameDto = gameService.getGameById(gameId);
        AccountDto accountDto = accountService.getAccountById(accountId);
        boolean hasReviewed = reviewService.existsByGameAndAccount(gameId, accountDto.getId());
        boolean accountHasGame = accountService.accountHasGame(accountId, gameId); 
        boolean editReview = true; 
        model.addAttribute("review", reviewDto); 
        model.addAttribute("game", gameDto);
        model.addAttribute("account", accountDto);
        model.addAttribute("hasReviewed", hasReviewed);
        model.addAttribute("editReview", editReview);
        model.addAttribute("accountHasGame", accountHasGame); 
        return "detail_page";
    }

    @PostMapping("/update-review/{reviewId}")
    public String updateReview( @ModelAttribute ReviewDto reviewDto, @PathVariable("reviewId") Long reviewId, Long gameId, Long accountId, Model model) {
        reviewService.updateReview(reviewDto); 
        RichGameDto gameDto = gameService.getGameById(gameId);
        AccountDto accountDto = accountService.getAccountById(accountId);
        boolean hasReviewed = reviewService.existsByGameAndAccount(gameId, accountDto.getId());
        boolean accountHasGame = accountService.accountHasGame(accountId, gameId); 
        boolean editReview = false; 
        model.addAttribute("review", reviewDto); 
        model.addAttribute("game", gameDto);
        model.addAttribute("account", accountDto);
        model.addAttribute("hasReviewed", hasReviewed);
        model.addAttribute("editReview", editReview);
        model.addAttribute("accountHasGame", accountHasGame); 
        return "detail_page";
    }


}
