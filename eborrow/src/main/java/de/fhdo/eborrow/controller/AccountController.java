package de.fhdo.eborrow.controller;

import de.fhdo.eborrow.dto.AccountDto;
import de.fhdo.eborrow.dto.GameDto;
import de.fhdo.eborrow.services.AccountService;
import de.fhdo.eborrow.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/thymeleaf/account")
public class AccountController {
	private final AccountService accountService;
	private final GameService gameService;

	@Autowired
	public AccountController(AccountService accountService, GameService gameService) {
		this.accountService = accountService;
		this.gameService = gameService;
	}

	@GetMapping("/{id}")
	public String getAccountById(@PathVariable Long id, Model model) {
		AccountDto accountDto = accountService.getAccountById(id);
		List<GameDto> gamesDtos = accountDto.getTaggedGames();

		model.addAttribute("account", accountDto);
		model.addAttribute("games", gamesDtos);

		return "profile_page";
	}

	@PostMapping("/{accountId}/unlistGame/{gameId}")
	public String unlistGameFromAccount(@PathVariable Long accountId, @PathVariable Long gameId, Model model) {
		AccountDto accountDto = accountService.getAccountById(accountId);
		accountService.unlistGameFromAccount(accountDto, gameId);

		List<GameDto> gamesDtos = accountDto.getTaggedGames();
		model.addAttribute("account", accountDto);
		model.addAttribute("games", gamesDtos);

		return "profile_page";
	}

	@GetMapping("/{id}/edit")
	public String showEditAccountView(@PathVariable Long id, Model model) {
		AccountDto accountDto = accountService.getAccountById(id);
		model.addAttribute("account", accountDto);

		return "profile_edit_page";
	}

	@PostMapping("/{id}/edit")
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

		AccountDto accountDto = accountService.getAccountById(id);
		List<GameDto> gamesDtos = accountDto.getTaggedGames();

		model.addAttribute("account", accountDto);
		model.addAttribute("games", gamesDtos);

		return "profile_page";
	}
}
