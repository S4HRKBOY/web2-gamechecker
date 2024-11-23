package de.fhdo.eborrow.services;

import de.fhdo.eborrow.converters.AccountMapper;
import de.fhdo.eborrow.domain.Account;
import de.fhdo.eborrow.dto.AccountDto;
import de.fhdo.eborrow.dto.GameDto;
import de.fhdo.eborrow.dto.RichAccountDto;
import de.fhdo.eborrow.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class AccountService {
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
		List<RichAccountDto> allRichDto = StreamSupport.stream(all.spliterator(), false)
				.map(AccountMapper::accountToRichDto)  // Cast all accounts to RichDTOs
				.toList();

		return allRichDto;
	}

	public AccountDto getAccountById(Long id) {
		Account account = accountRepository.findById(id).orElse(null);
		if (account == null) {
			return null;
		}

		AccountDto accountDto = AccountMapper.accountToDto(account);

		return accountDto;
	}

	public RichAccountDto getRichAccountById(Long id) {
		Account account = accountRepository.findById(id).orElse(null);
		if (account == null) {
			return null;
		}

		RichAccountDto richAccountDto = AccountMapper.accountToRichDto(account);

		return richAccountDto;
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

	// TODO Zak: Alternativer Ansatz fuer saubere Trennung: Ein UpdateAccount Objekt aus dem Request erzeugen, in dem nur die zu aendernden Felder gesetzt sind
	// und dann die Werte auf das existierende Objekt im Repository ueberfuehren

	// use in case the id in accountChanges is guaranteed to match the id in the database 
	public boolean updateAccount(AccountDto accountChanges) {
		return updateAccount(accountChanges, accountChanges.getId());
	}

	// use in case the id in accountChanges is not guaranteed to match the id in the database
	public boolean updateAccount(AccountDto accountChanges, Long id) {
		Account existingAccount = accountRepository.findById(id).orElse(null);
		if (existingAccount == null) {
			System.err.println("Update failed: No Account found with id " + id);
			return false;
		}

		AccountDto existingAccountDto = AccountMapper.accountToDto(existingAccount);

		return updateAccount(accountChanges, existingAccountDto);
	}

	public boolean updateAccount(AccountDto accountChanges, AccountDto existingAccountDto) {
		AccountDto updatedAccountDTO = transferChanges(existingAccountDto, accountChanges);
		Account updatedAccount = AccountMapper.dtoToAccount(updatedAccountDTO);
		accountRepository.save(updatedAccount);

		return true;
	}

	public boolean unlistGameFromAccount(Long accountId, Long gameId) {
		RichAccountDto richAccountDto = getRichAccountById(accountId);
		if (richAccountDto == null) {
			System.err.println("Unlisting failed: No Account found with id " + accountId);
			return false;
		}

		return unlistGameFromAccount(richAccountDto, gameId);
	}

	public boolean unlistGameFromAccount(RichAccountDto richAccountDto, Long gameId) {
		List<GameDto> gamesDtos = richAccountDto.getTaggedGames();
		if (gamesDtos == null) {
			System.err.println("Unlisting failed: No games found for Account with id " + richAccountDto.getId());
			return false;
		}

		List<GameDto> updatedGamesDtos = gamesDtos.stream().filter(gameDto -> !gameDto.getId().equals(gameId)).toList();
		transferTaggedGameChanges(richAccountDto, updatedGamesDtos);

		return true;
	}

	private RichAccountDto transferTaggedGameChanges(RichAccountDto existingRichAccountDto, List<GameDto> newTaggedGames){
		existingRichAccountDto.setTaggedGames(newTaggedGames);
		Account updatedAccount = AccountMapper.richDtoToAccount(existingRichAccountDto);
		accountRepository.save(updatedAccount);

		return existingRichAccountDto;
	}
	
	private AccountDto transferChanges(AccountDto existingAccountDto, AccountDto accountChanges) {
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

		return existingAccountDto;
	}
}
