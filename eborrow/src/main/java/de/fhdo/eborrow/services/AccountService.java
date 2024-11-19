package de.fhdo.eborrow.services;

import de.fhdo.eborrow.converters.AccountMapper;
import de.fhdo.eborrow.domain.Account;
import de.fhdo.eborrow.dto.AccountDto;
import de.fhdo.eborrow.dto.RichAccountDto;
import de.fhdo.eborrow.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class AccountService {
	// TODO Zak: Gehoeren hier auch Funktionen fuer AddGame, GetGames, GetGameById, DeleteGame hin?
	// TODO Zak: Gehoeren hier auch Funktionen fuer AddReview, GetReviews, GetReviewId und DeleteReview?
	private final AccountRepository accountRepository;

	@Autowired
	public AccountService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	public Long addAccount(AccountDto accountDto) {
		Account account = AccountMapper.dtoToAccount(accountDto);

		return accountRepository.save(account).getId();
	}

	public Iterable<AccountDto> getAccounts() {
		Iterable<Account> all = accountRepository.findAll();
		Iterable<AccountDto> allDto = StreamSupport.stream(all.spliterator(), false)
				.map(AccountMapper::accountToDto)  // Cast all accounts to DTOs
				.toList();

		return allDto;
	}

	public List<RichAccountDto> getRichAccounts() {
		Iterable<Account> all = accountRepository.findAll();
		List<RichAccountDto> allDto = StreamSupport.stream(all.spliterator(), false)
				.map(AccountMapper::accountToRichDto)  // Cast all accounts to DTOs
				.toList();

		return allDto;
	}

	public AccountDto getAccountById(Long id) {
		Account account = accountRepository.findById(id).orElse(null);
		if (account == null) {
			return null;
		}

		AccountDto accountDto = AccountMapper.accountToDto(account);

		return accountDto;
	}

	public boolean deleteAccount(Long id) {
		Account account = accountRepository.findById(id).orElse(null);
		if (account == null) {
			System.err.println("Delete failed: No Account found with id " + id);
			return false;
		}

		accountRepository.deleteById(id);
		return true;
	}

	// Zak: Alternativer Ansatz fuer saubere Trennung: Ein UpdateAccount Objekt erzeugen, in dem nur die zu aendernden Felder gesetzt sind
	// und dann die Werte auf das existierende Objekt im Repository ueberfuehren
	public boolean updateAccount(RichAccountDto accountChanges, Long id) {
		Account existingAccount = accountRepository.findById(id).orElse(null);
		if (existingAccount == null) {
			System.err.println("Update failed: No Account found with id " + id);
			return false;
		}

		RichAccountDto existingAccountDto = AccountMapper.accountToRichDto(existingAccount);
		RichAccountDto updatedAccountDTO = transferChanges(existingAccountDto, accountChanges);
		Account updatedAccount = AccountMapper.richDtoToAccount(updatedAccountDTO);
		accountRepository.save(updatedAccount);

		return true;
	}

	private RichAccountDto transferChanges(RichAccountDto existingAccountDto, RichAccountDto accountChanges) {
		if (accountChanges.getPrename() != null) {
			existingAccountDto.setPrename(accountChanges.getPrename());
		}

		if (accountChanges.getSurname() != null) {
			existingAccountDto.setSurname(accountChanges.getSurname());
		}

		if (accountChanges.getUsername() != null) {
			existingAccountDto.setUsername(accountChanges.getUsername());
		}

		if (accountChanges.getBirthday() != null) {
			existingAccountDto.setBirthday(accountChanges.getBirthday());
		}

		if (accountChanges.getEmail() != null) {
			existingAccountDto.setEmail(accountChanges.getEmail());
		}

		if (accountChanges.getPassword() != null) {
			existingAccountDto.setPassword(accountChanges.getPassword());
		}

		if (accountChanges.getProfilePicture() != null) {
			existingAccountDto.setProfilePicture(accountChanges.getProfilePicture());
		}

		// Zak: Sollte der Wechsel des Status auf Publisher bzw. User unterstuetzt werden?
		
		if (accountChanges.getTaggedGames() != null) {
			existingAccountDto.setTaggedGames(accountChanges.getTaggedGames());
		}
		
		return existingAccountDto;
	}
}
