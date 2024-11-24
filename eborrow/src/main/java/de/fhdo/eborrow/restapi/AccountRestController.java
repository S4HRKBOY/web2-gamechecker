package de.fhdo.eborrow.restapi;

import de.fhdo.eborrow.dto.AccountDto;
import de.fhdo.eborrow.dto.RichAccountDto;
import de.fhdo.eborrow.services.AccountService;
import de.fhdo.eborrow.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/account")
public class AccountRestController {
	private final AccountService accountService;
	private final GameService gameService;

	@Autowired
	public AccountRestController(AccountService accountService, GameService gameService) {
		this.accountService = accountService;
		this.gameService = gameService;
	}

	// ResponseEntity allows for multiple HTTP status codes to be returned
	@GetMapping(value = "/getAccounts", params = "with-games")
	public ResponseEntity<Iterable<RichAccountDto>> getAllRichAccounts() {
		Iterable<RichAccountDto> richAccountsDtos = accountService.getRichAccounts();
		if (richAccountsDtos == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(richAccountsDtos, HttpStatus.OK);
	}

	@GetMapping(value = "/getAccounts", params = "!with-games")
	public ResponseEntity<Iterable<AccountDto>> getAllAccounts() {
		Iterable<AccountDto> accountDtos = accountService.getAccounts();
		if (accountDtos == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(accountDtos, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}", params = "with-games")
	public ResponseEntity<RichAccountDto> getRichAccountById(@PathVariable Long id) {
		RichAccountDto richAccountDto = accountService.getRichAccountById(id);
		if (richAccountDto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(richAccountDto, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id) {
		AccountDto accountDto = accountService.getAccountById(id);
		if (accountDto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(accountDto, HttpStatus.OK);
	}

	@PostMapping(value = "/create-account", consumes = "application/json")
	public ResponseEntity<Long> createAccount(@RequestBody AccountDto accountDto) {
		if (accountDto == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Long accountId = accountService.addAccount(accountDto);
		if (accountId == null) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(accountId, HttpStatus.CREATED);
	}

	@PostMapping(value = "/edit/{id}", consumes = "application/json")
	public ResponseEntity<Void> updateAccount(@PathVariable Long id, @RequestBody AccountDto prefilledAccount) {
		if (prefilledAccount == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		AccountDto accountDto = accountService.getAccountById(id);
		if (accountDto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		boolean updateSucceeded = accountService.updateAccount(prefilledAccount, accountDto);
		if (!updateSucceeded) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping(value = "/delete-account", consumes = "application/json")
	public ResponseEntity<Void> deleteAccount(@RequestBody Map<String, Long> requestBody) {
		Long id = requestBody.get("Id");
		if (id == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		if (!accountService.deleteAccount(id)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping(value = "/add-game", consumes = "application/json")
	public ResponseEntity<Void> addGameToAccount(@RequestBody Map<String, Long> requestBody) {
		Long accountId = requestBody.get("accountId");
		Long gameId = requestBody.get("gameId");

		if (accountId == null || gameId == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		if (!accountService.addGameToAccount(accountId, gameId)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping(value = "/unlist-game", consumes = "application/json")
	public ResponseEntity<Void> unlistGameFromAccount(@RequestBody Map<String, Long> requestBody) {
		Long accountId = requestBody.get("accountId");
		Long gameId = requestBody.get("gameId");

		if (accountId == null || gameId == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		if (!accountService.unlistGameFromAccount(accountId, gameId)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}
}
