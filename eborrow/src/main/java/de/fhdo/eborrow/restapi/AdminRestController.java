package de.fhdo.eborrow.restapi;

import de.fhdo.eborrow.dto.AccountDto;
import de.fhdo.eborrow.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminRestController {
	private final AccountService accountService;

	@Autowired
	public AdminRestController(AccountService accountService) {this.accountService = accountService;}

	@GetMapping(value = "/account/set-publisher-status/{id}")
	public ResponseEntity<Void> setPublisherStatus(@PathVariable Long id, @RequestParam("is-publisher") boolean isPublisher) {
		AccountDto accountDto = accountService.getAccountById(id);
		if (accountDto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		if (!accountService.updatePublisherStatus(accountDto, isPublisher)) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}
}
