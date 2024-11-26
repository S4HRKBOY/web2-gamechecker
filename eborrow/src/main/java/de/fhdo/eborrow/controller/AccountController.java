package de.fhdo.eborrow.controller;

import de.fhdo.eborrow.dto.GameDto;
import de.fhdo.eborrow.dto.RichAccountDto;
import de.fhdo.eborrow.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/thymeleaf/account")
public class AccountController {
	private final AccountService accountService;

	@Autowired
	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@GetMapping("/{id}")
	public String showProfilePageView(@PathVariable Long id, Model model) {
		RichAccountDto richAccountDto = accountService.getRichAccountById(id);
		List<GameDto> gamesDtos = richAccountDto.getTaggedGames();
		model.addAttribute("account", richAccountDto);
		model.addAttribute("games", gamesDtos);

		return "profile_page";
	}

	@PostMapping("/unlist-game")
	public String unlistGameFromAccount(@RequestParam("account-Id") Long accountId, @RequestParam("game-Id") Long gameId, Model model) {
		RichAccountDto richAccountDto = accountService.getRichAccountById(accountId);
		accountService.unlistGameFromAccount(richAccountDto, gameId);

		List<GameDto> gamesDtos = richAccountDto.getTaggedGames();
		model.addAttribute("account", richAccountDto);
		model.addAttribute("games", gamesDtos);

		return "profile_page";
	}
}
