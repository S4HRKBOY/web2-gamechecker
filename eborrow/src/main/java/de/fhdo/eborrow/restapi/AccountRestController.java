package de.fhdo.eborrow.restapi;

import de.fhdo.eborrow.dto.AccountDto;
import de.fhdo.eborrow.dto.RichAccountDto;
import de.fhdo.eborrow.services.AccountService;
import jakarta.validation.Valid;
import javassist.NotFoundException;
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
	@GetMapping(value = "/{id}", params = "with-games", produces = "application/json")
	public ResponseEntity<RichAccountDto> getRichAccountById(@PathVariable Long id) throws NotFoundException {
		RichAccountDto richAccountDto = accountService.getRichAccountById(id);
		
		if (richAccountDto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(richAccountDto, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id) throws NotFoundException {
		AccountDto accountDto = accountService.getAccountById(id);
		if (accountDto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(accountDto, HttpStatus.OK);
	}

	@PostMapping(value = "/create-account", produces = "application/json", consumes = {"application/json", "application/xml"})
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

	@PutMapping(value = "/edit/{id}", produces = "application/json", consumes = {"application/json", "application/xml"})
	public ResponseEntity<Void> updateAccount(@PathVariable Long id, @Valid @RequestBody AccountDto prefilledAccount) throws NotFoundException {
		if (prefilledAccount == null || prefilledAccount.getId() == null || !id.equals(prefilledAccount.getId())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		accountService.updateAccount(id, prefilledAccount);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping(value = "/delete-account/{id}", produces = "application/json")
	public ResponseEntity<Void> deleteAccount(@PathVariable Long id) throws NotFoundException {
		if (id == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		accountService.deleteAccount(id);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping(value = "/add-game", produces = "application/json", consumes = {"application/json", "application/xml"})
	public ResponseEntity<Void> addGameToAccount(@RequestBody Map<String, Long> requestBody) throws NotFoundException {
		Long accountId = requestBody.get("account-Id");
		Long gameId = requestBody.get("game-Id");

		if (accountId == null || gameId == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		accountService.addGameToAccount(accountId, gameId);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping(value = "/unlist-game", produces = "application/json", consumes = {"application/json", "application/xml"})
	public ResponseEntity<Void> unlistGameFromAccount(@RequestBody Map<String, Long> requestBody) throws NotFoundException {
		Long accountId = requestBody.get("account-Id");
		Long gameId = requestBody.get("game-Id");

		if (accountId == null || gameId == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		accountService.unlistGameFromAccount(accountId, gameId);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
