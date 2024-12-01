package de.fhdo.eborrow.graphqlapi;

import de.fhdo.eborrow.dto.AccountDto;
import de.fhdo.eborrow.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class AdminGraphClController {
	private final AccountService accountService;

	@Autowired
	public AdminGraphClController(AccountService accountService) {
		this.accountService = accountService;
	}

	@MutationMapping("setPublisherStatus")
	public AccountDto setPublisherStatus(@Argument Long id, @Argument boolean isPublisher) {
		boolean succeeded = accountService.updatePublisherStatus(id, isPublisher);

		return accountService.getAccountById(id);
	}
}
