package de.fhdo.eborrow.graphqlapi;

import de.fhdo.eborrow.dto.AccountDto;
import de.fhdo.eborrow.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class AdminGraphClController {
	private final AccountService accountService;

	@Autowired
	public AdminGraphClController(AccountService accountService) {
		this.accountService = accountService;
	}

	@MutationMapping("setPublisherStatus")
	public AccountDto setPublisherStatus(@Argument Long id, @Argument boolean isPublisher) {
		if (id == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID is required");
		}

		boolean succeeded = accountService.updatePublisherStatus(id, isPublisher);
		if (!succeeded) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}

		return accountService.getAccountById(id);
	}
}
