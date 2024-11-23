package de.fhdo.eborrow.controller;

import de.fhdo.eborrow.dto.AccountDto;
import de.fhdo.eborrow.dto.GameDto;
import de.fhdo.eborrow.dto.RichAccountDto;
import de.fhdo.eborrow.services.AccountService;
import de.fhdo.eborrow.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {
	private final AccountService accountService;
	private final GameService gameService;

	@Autowired
	public AccountController(AccountService accountService, GameService gameService) {
		this.accountService = accountService;
		this.gameService = gameService;
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

	@GetMapping("/edit/{id}")
	public String showEditAccountView(@PathVariable Long id, Model model) {
		AccountDto accountDto = accountService.getAccountById(id);
		model.addAttribute("account", accountDto);

		return "profile_edit_page";
	}

	@PostMapping("/edit/{id}")
	public String updateAccount(@PathVariable Long id, @RequestBody AccountDto prefilledAccount, Model model) {
		if (prefilledAccount == null) {
			// TODO: Redirect to /{id}/edit
			return showEditAccountView(id, model);
		}

		boolean updateSucceeded = accountService.updateAccount(prefilledAccount, id);
		if (!updateSucceeded) {
			// TODO: Redirect to /{id}/edit
			return showEditAccountView(id, model);
		}

		RichAccountDto richAccountDto = accountService.getRichAccountById(id);
		List<GameDto> gamesDtos = richAccountDto.getTaggedGames();

		model.addAttribute("account", richAccountDto);
		model.addAttribute("games", gamesDtos);

		return "profile_page";
	}
	
	@PostMapping("/delete-account")
	public String deleteAccount(@RequestParam("account-Id") Long accountId) {
		// TODO Zak: Bestätigung vom User holen? (eigene View oder ueber Prompt via JavaScript mit funktion confirm())
		accountService.deleteAccount(accountId);

		return "login_page";
	}
}
