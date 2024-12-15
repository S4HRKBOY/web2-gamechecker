package de.fhdo.eborrow.restapi;

import de.fhdo.eborrow.services.AccountService;
import javassist.NotFoundException;
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

	@PutMapping(value = "/account/set-publisher-status/{id}", produces = "application/json", consumes = {"application/json", "application/xml"})
	public ResponseEntity<Void> setPublisherStatus(@PathVariable Long id, @RequestParam("is-publisher") boolean isPublisher) throws NotFoundException {
		accountService.updatePublisherStatus(id, isPublisher);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
