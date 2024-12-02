package de.fhdo.eborrow.graphqlapi;

import de.fhdo.eborrow.dto.AccountDto;
import de.fhdo.eborrow.dto.RichAccountDto;
import de.fhdo.eborrow.services.AccountService;
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
	public RichAccountDto getRichAccountById(@Argument Long id) {
		RichAccountDto richAccountDto = accountService.getRichAccountById(id);
		if (richAccountDto == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		return richAccountDto;
	}

	@MutationMapping("createAccount")
	public AccountDto createAccount(@Argument AccountDto account) {
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
	public AccountDto updateAccount(@Argument AccountDto account) {
		if (account == null || account.getId() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}

		boolean succeeded = accountService.updateAccount(account);
		if (!succeeded) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}

		AccountDto result = accountService.getAccountById(account.getId());

		return result;
	}

	@MutationMapping("deleteAccount")
	public boolean deleteAccount(@Argument Long id) {
		if (id == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID is required");
		}

		boolean succeeded = accountService.deleteAccount(id);

		return succeeded;
	}

	@MutationMapping("addGameToAccount")
	public RichAccountDto addGameToAccount(@Argument Long accountId, @Argument Long gameId) {
		if (accountId == null || gameId == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID is required");
		}

		boolean succeeded = accountService.addGameToAccount(accountId, gameId);
		if (!succeeded) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}

		RichAccountDto result = accountService.getRichAccountById(accountId);

		return result;
	}

	@MutationMapping("unlistGameFromAccount")
	public RichAccountDto unlistGameFromAccount(@Argument Long accountId, @Argument Long gameId) {
		if (accountId == null || gameId == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID is required");
		}

		boolean succeeded = accountService.unlistGameFromAccount(accountId, gameId);
		if (!succeeded) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}

		RichAccountDto result = accountService.getRichAccountById(accountId);

		return result;
	}
}
