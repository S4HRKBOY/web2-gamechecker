package de.fhdo.eborrow.services;

import de.fhdo.eborrow.converters.AccountMapper;
import de.fhdo.eborrow.domain.account.Account;
import de.fhdo.eborrow.dto.account.AccountDto;
import de.fhdo.eborrow.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.StreamSupport;

@Service
public class AccountService {
	private final AccountMapper accountMapper;
	private final AccountRepository accountRepository;

	@Autowired
	public AccountService(AccountMapper accountMapper, AccountRepository accountRepository) {
		this.accountMapper = accountMapper;
		this.accountRepository = accountRepository;
	}

	public Long addAccount(AccountDto accountDto) {
		Account account = accountMapper.dtoToAccount(accountDto);

		return accountRepository.save(account).getId();
	}

	public Iterable<AccountDto> getAccounts() {
		Iterable<Account> all = accountRepository.findAll();
		Iterable<AccountDto> allDto = StreamSupport.stream(all.spliterator(), false)
				.map(accountMapper::accountToDto)  // Cast all accounts to DTOs
				.toList();

		return allDto;
	}

	public AccountDto getAccountById(Long id) {
		Account account = accountRepository.findById(id).orElse(null);
		if (account == null) {
			return null;
		}

		AccountDto accountDto = accountMapper.accountToDto(account);

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

	// TODO Zak: Besser anders herum: oldAccountDto mit den Werten von newAccountDto befuellen, damit nicht eins versehentlich vergessen wird
	// Zak: Alternativer Ansatz: Ein UpdateAccount Objekt erzeugen, in dem nur die zu aendernden Felder gesetzt sind
	// und dann die Werte auf das existierende Objekt im Repository ueberfuehren
	public boolean updateAccount(AccountDto newAccountDto, Long id) {
		Account existingAccount = accountRepository.findById(id).orElse(null);
		if (existingAccount == null) {
			System.err.println("Update failed: No Account found with id " + id);
			return false;
		}

		newAccountDto.setId(id);
		AccountDto existingAccountDto = accountMapper.accountToDto(existingAccount);
		copyExistingFields(existingAccountDto, newAccountDto);
		Account newAccount = accountMapper.dtoToAccount(newAccountDto);
		accountRepository.save(newAccount);

		return true;
	}

	private void copyExistingFields(AccountDto existingAccountDto, AccountDto newAccountDto) {
		if (newAccountDto.getPrename() == null) {
			newAccountDto.setPrename(existingAccountDto.getPrename());
		}

		if (newAccountDto.getSurname() == null) {
			newAccountDto.setSurname(existingAccountDto.getSurname());
		}

		if (newAccountDto.getUsername() == null) {
			newAccountDto.setUsername(existingAccountDto.getUsername());
		}

		if (newAccountDto.getBirthday() == null) {
			newAccountDto.setBirthday(existingAccountDto.getBirthday());
		}

		if (newAccountDto.getEmail() == null) {
			newAccountDto.setEmail(existingAccountDto.getEmail());
		}

		if (newAccountDto.getPassword() == null) {
			newAccountDto.setPassword(existingAccountDto.getPassword());
		}

		if (newAccountDto.getProfilePicture() == null) {
			newAccountDto.setProfilePicture(existingAccountDto.getProfilePicture());
		}
	}
}
