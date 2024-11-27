package de.fhdo.eborrow.graphqlapi;

import de.fhdo.eborrow.dto.AccountDto;
import de.fhdo.eborrow.dto.RichAccountDto;
import de.fhdo.eborrow.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class AccountGraphQlController {
	private final AccountService accountService;

	@Autowired
	public AccountGraphQlController(AccountService accountService) {this.accountService = accountService;}

	@QueryMapping("accounts")
	public Iterable<AccountDto> getAllAccounts() {
		return accountService.getAccounts();
	}

	// TODO Zak: Fehlerstatus uebertragen?
	@QueryMapping("account")
	public RichAccountDto getAccountById(@Argument Long id) {
		return accountService.getRichAccountById(id);
	}

	@MutationMapping("createAccount")
	public AccountDto createAccount(@Argument AccountDto account) {
		var accountId = accountService.addAccount(account);

		return accountService.getAccountById(accountId);
	}

	@MutationMapping("updateAccount")
	public AccountDto updateAccount(@Argument AccountDto account) {
		boolean succeeded = accountService.updateAccount(account);
		AccountDto result = accountService.getAccountById(account.getId());

		return result;
	}

	@MutationMapping("deleteAccount")
	public boolean deleteAccount(@Argument Long id) {
		return accountService.deleteAccount(id);
	}

	@MutationMapping("addGameToAccount")
	public RichAccountDto addGameToAccount(@Argument Long accountId, @Argument Long gameId) {
		boolean succeeded = accountService.addGameToAccount(accountId, gameId);
		RichAccountDto result = accountService.getRichAccountById(accountId);

		return result;
	}

	@MutationMapping("unlistGameFromAccount")
	public RichAccountDto unlistGameFromAccount(@Argument Long accountId, @Argument Long gameId) {
		boolean succeeded = accountService.unlistGameFromAccount(accountId, gameId);
		RichAccountDto result = accountService.getRichAccountById(accountId);

		return result;
	}
}
