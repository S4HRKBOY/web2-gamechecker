package de.fhdo.eborrow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import de.fhdo.eborrow.dto.ReviewDto;
import de.fhdo.eborrow.dto.RichAccountDto;
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
    public String reviewSubmit(@ModelAttribute ReviewDto reviewDto, @RequestParam("gameId") Long gameId, @RequestParam("accountId") Long accountId, Model model) {
        reviewService.addReview(reviewDto, gameId, accountId);
        model.addAttribute("review", reviewDto);
        RichGameDto gameDto = gameService.getGameById(gameId);
        RichAccountDto accountDto = accountService.getRichAccountById(1L);
        boolean hasReviewed = reviewService.existsByGameAndAccount(gameId, accountDto.getId());
        model.addAttribute("game", gameDto);
        model.addAttribute("account", accountDto);
        model.addAttribute("hasReviewed", hasReviewed);
        return "detail_page";
    }

}
