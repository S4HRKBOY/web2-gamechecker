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
		if (accountId == null || gameId == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID is required");
		}

		accountService.addGameToAccount(accountId, gameId);
		RichAccountDto result = accountService.getRichAccountById(accountId);

		return result;
	}

	@MutationMapping("unlistGameFromAccount")
	public RichAccountDto unlistGameFromAccount(@Argument Long accountId, @Argument Long gameId) throws NotFoundException {
		if (accountId == null || gameId == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID is required");
		}

		accountService.unlistGameFromAccount(accountId, gameId);
		RichAccountDto result = accountService.getRichAccountById(accountId);

		return result;
	}
}
