package de.fhdo.eborrow.graphqlapi;

import de.fhdo.eborrow.dto.AccountDto;
import de.fhdo.eborrow.dto.RichAccountDto;
import de.fhdo.eborrow.services.AccountService;
import jakarta.validation.Valid;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class AccountGraphQlController {
	private final AccountService accountService;

	@Autowired
	public AccountGraphQlController(AccountService accountService) {
		this.accountService = accountService;
	}

	@QueryMapping("accountId")
	public Long fetchAccountId(@Argument String username, @Argument String password) throws NotFoundException {
		if (username == null || username.trim().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username is required");
		}

		if (password == null || password.trim().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "password is required");
		}

		Long accountId = accountService.fetchAccountId(username, password);
		if (accountId == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		return accountId;
	}
	
	@QueryMapping("accounts")
	public Iterable<AccountDto> getAllAccounts() {
		return accountService.getAccounts();
	}

	@QueryMapping("account")
	public RichAccountDto getRichAccountById(@Argument Long id) throws NotFoundException {
		RichAccountDto richAccountDto = accountService.getRichAccountById(id);
		if (richAccountDto == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		return richAccountDto;
	}

	@MutationMapping("createAccount")
	public AccountDto createAccount(@Valid @Argument AccountDto account) throws NotFoundException {
		if (account == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}

		Long accountId = accountService.addAccount(account);
		if (accountId == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}

		AccountDto result = accountService.getAccountById(accountId);

		return result;
	}

	@MutationMapping("updateAccount")
	public AccountDto updateAccount(@Valid @Argument AccountDto account) throws NotFoundException {
		if (account == null || account.getId() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}

		accountService.updateAccount(account);
		AccountDto result = accountService.getAccountById(account.getId());

		return result;
	}

	@MutationMapping("deleteAccount")
	public boolean deleteAccount(@Argument Long id) throws NotFoundException {
		if (id == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID is required");
		}

		accountService.deleteAccount(id);

		return true;
	}

	@MutationMapping("addGameToAccount")
	public RichAccountDto addGameToAccount(@Argument Long accountId, @Argument Long gameId) throws NotFoundException {
		if (accountId == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "accountID is required");
		}

		if (gameId == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "gameID is required");
		}

		accountService.addGameToAccount(accountId, gameId);
		RichAccountDto result = accountService.getRichAccountById(accountId);

		return result;
	}

	@MutationMapping("unlistGameFromAccount")
	public RichAccountDto unlistGameFromAccount(@Argument Long accountId, @Argument Long gameId) throws NotFoundException {
		if (accountId == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "accountID is required");
		}

		if (gameId == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "gameID is required");
		}

		accountService.unlistGameFromAccount(accountId, gameId);
		RichAccountDto result = accountService.getRichAccountById(accountId);

		return result;
	}

	@QueryMapping("usernameTaken")
	public boolean isUsernameTaken(@Argument String username) {
		if (username == null || username.trim().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}

		return accountService.isUsernameTaken(username);
	}
	
	@QueryMapping("usernameUsedByOtherAccount")
	public boolean isUsernameUsedByOtherAccount(@Argument Long id, @Argument String username) throws NotFoundException {
		if (id == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID is required");
		}

		if (username == null || username.trim().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}

		return accountService.isUsernameUsedByOtherAccount(id, username);
	}

	@QueryMapping("emailTaken")
	public boolean isEmailTaken(@Argument String email) {
		if (email == null || email.trim().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}

		return accountService.isEmailTaken(email);
	}

	@QueryMapping("emailUsedByOtherAccount")
	public boolean isEmailUsedByOtherAccount(@Argument Long id, @Argument String email) throws NotFoundException {
		if (id == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID is required");
		}

		if (email == null || email.trim().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}

		return accountService.isEmailUsedByOtherAccount(id, email);
	}

	@QueryMapping("accountHasGame")
	public Boolean accountHasGame(@Argument Long accountId, @Argument Long gameId) {
		return accountService.accountHasGame(accountId, gameId);
	}
}
