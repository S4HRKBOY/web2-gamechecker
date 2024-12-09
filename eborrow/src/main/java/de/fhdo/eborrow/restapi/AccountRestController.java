package de.fhdo.eborrow.restapi;

import de.fhdo.eborrow.dto.AccountDto;
import de.fhdo.eborrow.dto.RichAccountDto;
import de.fhdo.eborrow.services.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/account")
public class AccountRestController {
	private final AccountService accountService;

	@Autowired
	public AccountRestController(AccountService accountService) {
		this.accountService = accountService;
	}

	// ResponseEntity allows for multiple kinds of HTTP status codes to be returned
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
	public ResponseEntity<Long> createAccount(@Valid @RequestBody AccountDto accountDto) {
		if (accountDto == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Long accountId = accountService.addAccount(accountDto);
		if (accountId == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(accountId, HttpStatus.CREATED);
	}

	@PutMapping(value = "/edit/{id}", consumes = "application/json")
	public ResponseEntity<Void> updateAccount(@PathVariable Long id, @Valid @RequestBody AccountDto prefilledAccount) {
		if (prefilledAccount == null || prefilledAccount.getId() == null || !id.equals(prefilledAccount.getId())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		boolean succeeded = accountService.updateAccount(id, prefilledAccount);
		if (!succeeded) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping(value = "/delete-account/{id}", consumes = "application/json")
	public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
		if (id == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		boolean succeeded = accountService.deleteAccount(id);
		if (!succeeded) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping(value = "/add-game", consumes = "application/json")
	public ResponseEntity<Void> addGameToAccount(@RequestBody Map<String, Long> requestBody) {
		Long accountId = requestBody.get("account-Id");
		Long gameId = requestBody.get("game-Id");

		if (accountId == null || gameId == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		boolean succeeded = accountService.addGameToAccount(accountId, gameId);
		if (!succeeded) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping(value = "/unlist-game", consumes = "application/json")
	public ResponseEntity<Void> unlistGameFromAccount(@RequestBody Map<String, Long> requestBody) {
		Long accountId = requestBody.get("account-Id");
		Long gameId = requestBody.get("game-Id");

		if (accountId == null || gameId == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		boolean succeeded = accountService.unlistGameFromAccount(accountId, gameId);
		if (!succeeded) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	// TODO Zak: Handler fuer Exceptions bei SpringValidierung schreiben
}
